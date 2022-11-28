package com.departament.controller;

import com.departament.controller.config.TestContainerCouchbaseConfig;
import com.departament.controller.util.RequestGenerator;
import com.departament.requestservice.dto.RequestDto;
import com.departament.requestservice.entity.Request;
import com.departament.requestservice.repo.RequestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

public class RequestControllerTests extends TestContainerCouchbaseConfig {

    private RequestDto requestDto;

    @Autowired
    private RequestRepository requestRepository;

    @BeforeEach
    public void initEach() {
        requestDto = RequestGenerator.requestGenerator();
    }

    @AfterEach
    public void destroy() {
        requestRepository.deleteAll();
    }

    @Test
    @Sql(value = "classpath:db/create.n1ql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(value = "classpath:db/delete.n1ql", executionPhase = AFTER_TEST_METHOD)
    void findAll() throws Exception {
        List<Request> requests = requestRepository.findAll();
        Assertions.assertTrue(requests.size() > 0);
    }

}
