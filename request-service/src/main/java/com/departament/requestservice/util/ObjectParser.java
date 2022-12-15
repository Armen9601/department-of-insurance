package com.departament.requestservice.util;

import com.departament.requestservice.entity.Synthesizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ObjectParser {

    private final ObjectMapper mapper = new ObjectMapper();

    public Synthesizer parseStringToObject(String content) throws IOException {
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(content, Synthesizer.class);
    }
}