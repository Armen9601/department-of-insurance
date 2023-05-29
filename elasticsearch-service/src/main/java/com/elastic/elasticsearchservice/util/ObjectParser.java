package com.elastic.elasticsearchservice.util;

import com.elastic.elasticsearchservice.dto.NestedObject;
import com.elastic.elasticsearchservice.dto.RequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ObjectParser {

    private final ObjectMapper mapper = new ObjectMapper();

    public NestedObject parseStringToObject(String content) throws IOException {
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(content, NestedObject.class);
    }

    public RequestDto parseStringToRequest(String content) throws IOException {
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(content, RequestDto.class);
    }


}