package com.elastic.elasticsearchservice.service.impl;

import com.elastic.elasticsearchservice.dto.NestedObject;
import com.elastic.elasticsearchservice.dto.SynthesizerDto;
import com.elastic.elasticsearchservice.service.ElasticsearchService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    private final ObjectMapper objectMapper;
    @Value("${elastic.host}")
    private String host;
    @Value("${mappingJson}")
    String mappingJson;

    public ElasticsearchServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void indexDocument(String index, String id, Object source) throws IOException {
        String urlString = String.format("http://%s/%s/_doc/%s", host, index, id);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(urlString);
        httpPost.setHeader("Content-Type", "application/json");

        JsonObject requestBody = new JsonObject();
        String value = objectMapper.writeValueAsString(source);
//        requestBody.addProperty("value", value);
//
//        JsonObject indexSettings = new JsonObject();
//        indexSettings.addProperty("index.mapper.dynamic", false);
//
//        JsonObject rootObject = new JsonObject();
//        rootObject.add("settings", indexSettings);
//        rootObject.add("mappings", JsonParser.parseString(mappingJson));
//
//        requestBody.add("synthesizer", rootObject);
//        String mappedValue = String.format(mappingJson, value);

        StringEntity requestEntity = new StringEntity(value, ContentType.APPLICATION_JSON);
        httpPost.setEntity(requestEntity);

        HttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Indexing result: " + statusCode);
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
            InputStream inputStream = responseEntity.getContent();
            String responseString = EntityUtils.toString(responseEntity);
            System.out.println("Response: " + responseString);
            inputStream.close();
        }
    }


    @Override
    public List<NestedObject> searchDocuments(String index, String field, String value) throws IOException {
        String urlString = String.format("http://%s/%s/_search", host, index);
        String requestBody = String.format("{\"query\":{\"match\":{\"%s\":\"%s\"}}}", field, value);
        List<String> response = response(requestBody, urlString);
        return mapToNested(response);
    }

    @Override
    public List<String> searchDoc(String index, String field, String value) throws IOException {
        String urlString = String.format("http://%s/%s/_search", host, index);
        String requestBody = String.format("{\"query\":{\"match\":{\"%s\":\"%s\"}}}", field, value);
        return response(requestBody, urlString);
    }

    @Override
    public List<NestedObject> searchDocuments(List<String> indexes) throws IOException {
        String urlString = String.format("http://%s/%s/_search", host, String.join(",", indexes));
        String requestBody = "{}";
        List<String> response = response(requestBody, urlString);
        return mapToNested(response);
    }

    @Override
    public List<NestedObject> searchDocumentsByNestedField(String index, String nestedField, String nestedValue) throws IOException {
        String urlString = String.format("http://%s/%s/_search", host, index);
        String requestBody = String.format(
                "{ \"query\": { \"nested\": { \"path\": \"nestedObject\", \"query\": { \"match\": { \"nestedObject.%s\": \"%s\" } } } } }",
                nestedField,
                nestedValue
        );
        List<String> response = response(requestBody, urlString);
        return mapToNested(response);
    }


    @Override
    public void updateDocument(String index, String id, NestedObject updatedSource) throws IOException {
        String urlString = String.format("http://%s/%s/_doc/%s", host, index, id);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPut httpPut = new HttpPut(urlString);
        httpPut.setHeader("Content-Type", "application/json");
        String updatedValue = objectMapper.writeValueAsString(updatedSource);
        StringEntity requestEntity = new StringEntity(updatedValue, ContentType.APPLICATION_JSON);
        httpPut.setEntity(requestEntity);
        HttpResponse response = httpClient.execute(httpPut);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Updating result: " + statusCode);
        response.getEntity().getContent().close();
    }

    @Override
    public void deleteAllDocuments(String index) throws IOException {
        String urlString = String.format("http://%s/%s/_delete_by_query", host, index);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(urlString);
        httpPost.setHeader("Content-Type", "application/json");
        String requestBody = "{\"query\": {\"match_all\": {}}}";
        StringEntity requestEntity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
        httpPost.setEntity(requestEntity);
        HttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Deletion result: " + statusCode);
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
            InputStream inputStream = responseEntity.getContent();
            String responseString = EntityUtils.toString(responseEntity);
            System.out.println("Response: " + responseString);
            inputStream.close();
        }
    }


    private List<String> response(String requestBody, String urlString) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(urlString);
        httpPost.setHeader("Content-Type", "application/json");
        StringEntity requestEntity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
        httpPost.setEntity(requestEntity);
        HttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            throw new RuntimeException("Search request failed: " + statusCode);
        }
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
            InputStream inputStream = responseEntity.getContent();
            String responseBody = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
            inputStream.close();
            JsonNode hits = objectMapper.readTree(responseBody).path("hits").path("hits");
            return StreamSupport.stream(hits.spliterator(), false)
                    .map(hit -> hit.path("_source").toString())
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private List<NestedObject> mapToNested(List<String> responseBody) {
        return responseBody.stream()
                .map(json -> {
                    try {
                        return objectMapper.readValue(json, NestedObject.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }
}
