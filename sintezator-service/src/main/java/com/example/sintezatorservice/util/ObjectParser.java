package com.example.sintezatorservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class ObjectParser {

    private final AtomicReference<ObjectMapper> objectMapper = new AtomicReference<>(new ObjectMapper());

    public String parseObjectToJson(Object object) throws JsonProcessingException {
        objectMapper.get().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.get().registerModule(new JavaTimeModule());
        return objectMapper.get().writeValueAsString(object);
    }

}