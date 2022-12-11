package com.department.requestservice.controller;

import com.departament.requestservice.RequestServiceApplication;
import com.departament.requestservice.dto.RequestDto;
import com.departament.requestservice.repo.RequestRepository;
import com.department.requestservice.config.CouchbaseConfig;
import com.department.requestservice.util.DataGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.UUID;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = RequestServiceApplication.class)
@AutoConfigureMockMvc
@DirtiesContext
@TestPropertySource(value = "application-test.yml")
class RequestControllerTest extends CouchbaseConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RequestRepository requestRepository;

    private RequestDto requestDto;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void init() {
        requestDto = DataGenerator.generatorRequestDtoData();
    }

    @AfterEach
    void destroy() {
        requestRepository.deleteAll();
    }

    @Test
    @Sql(value = "classpath:resources/create.sql", executionPhase = BEFORE_TEST_METHOD)
    void saveRequest() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/request")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(parseObjectToJson(requestDto)))
                .andExpect(status().isOk());
    }


    @Test
    @Sql(value = "classpath:resources/create.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:resources/delete.sql", executionPhase = AFTER_TEST_METHOD)
    void deleteRequestByIdSuccess() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/request/", "8a27c73e-76d2-4376-82ae-1cec166170f6"))
                .andExpect(status().isOk());
    }


    @Test
    @Sql(value = "classpath:resources/create.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:resources/delete.sql", executionPhase = AFTER_TEST_METHOD)
    void findRequestByIdSuccess() throws Exception {
        MvcResult mvcResult =
                mockMvc
                        .perform(MockMvcRequestBuilders.get("/request/", "8a27c73e-76d2-4376-82ae-1cec166170f6"))
                        .andExpect(status().isOk())
                        .andReturn();
        requestDto = parseStringToObject(mvcResult.getResponse().getContentAsString());
        Assertions.assertEquals(requestDto.getApplicantId(), UUID.fromString("8a27c73e-76d2-4376-82ae-1cec166170f6"));
    }

    @Test
    @Sql(value = "classpath:resources/create.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:resources/delete.sql", executionPhase = AFTER_TEST_METHOD)
    void findRequestByIdFail() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/request/", "8a27c73e-76d2-4376-82ae-1cec1661486g9"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(value = "classpath:resources/create.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:resources/delete.sql", executionPhase = AFTER_TEST_METHOD)
    void updateRequestSuccess() throws Exception {
        requestDto.setApplicantId(UUID.fromString("8a27c73e-76d2-4376-82ae-1cec1661486g9"));

        MvcResult mvcResult =
                mockMvc
                        .perform(
                                MockMvcRequestBuilders.put("/request/", "8a27c73e-76d2-4376-82ae-1cec166170f6")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(parseObjectToJson(requestDto)))
                        .andExpect(status().isOk())
                        .andReturn();
        requestDto = parseStringToObject(mvcResult.getResponse().getContentAsString());
        Assertions.assertEquals(requestDto.getApplicantId(), UUID.fromString("8a27c73e-76d2-4376-82ae-1cec166170f6"));
    }


    @Test
    void updateRequestFail() throws Exception {
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/request/", "8a27c73e-76d2-4376-82ae-1cec555555f5")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(parseObjectToJson(requestDto)))
                .andExpect(status().isNotFound());
    }


    private String parseObjectToJson(Object object) throws JsonProcessingException, com.fasterxml.jackson.core.JsonProcessingException {
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(object);
    }

    private RequestDto parseStringToObject(String content) throws IOException {
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(content, RequestDto.class);
    }

}
