package com.elastic.elasticsearchservice.service;

import com.elastic.elasticsearchservice.dto.NestedObject;
import com.elastic.elasticsearchservice.dto.RequestDto;

import java.io.IOException;
import java.util.List;

public interface ElasticsearchService {

    void indexDocument(String index, String id, Object source) throws IOException;

    List<NestedObject> searchDocuments(String index, String field, String value) throws IOException;

    List<String> searchDoc(String index, String field, String value) throws IOException;

    List<NestedObject> searchDocuments(List<String> indexes) throws IOException;

    void updateDocument(String index, String id, NestedObject updatedSource) throws IOException;

    List<NestedObject> searchDocumentsByNestedField(String index, String nestedField, String nestedValue) throws IOException;

    void deleteAllDocuments(String index) throws IOException;

}
