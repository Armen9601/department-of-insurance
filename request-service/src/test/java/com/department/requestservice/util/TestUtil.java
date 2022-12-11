package com.department.requestservice.util;

import com.departament.requestservice.dto.RequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.test.context.TestComponent;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

@TestComponent
public class TestUtil {

    private final ObjectMapper mapper = new ObjectMapper();

    public String parseObjectToJson(Object object) throws JsonProcessingException, com.fasterxml.jackson.core.JsonProcessingException {
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(object);
    }

    public RequestDto parseStringToObject(String content) throws IOException {
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(content, RequestDto.class);
    }
}