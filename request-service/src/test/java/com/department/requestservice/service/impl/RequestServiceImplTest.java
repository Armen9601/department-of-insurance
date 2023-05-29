package com.department.requestservice.service.impl;

import com.departament.requestservice.dto.RequestDto;
import com.departament.requestservice.entity.Request;
import com.departament.requestservice.exception.EntityNotFoundException;
import com.departament.requestservice.mapper.RequestMapper;
import com.departament.requestservice.repo.RequestRepository;
import com.departament.requestservice.service.impl.RequestServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RequestServiceImplTest {

    @InjectMocks
    private RequestServiceImpl requestService;

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private RequestMapper requestMapper;

    private Request request;

    private RequestDto requestDto;


    @BeforeEach
    void init() {

        request = new Request(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), LocalDateTime.now());
//        requestDto = new RequestDto(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), UUID.fromString("123e4567-e89b-12d3-a456-426614174001"), LocalDateTime.now());
    }

    @Test
    void addSuccess() {
        when(requestMapper.toEntity(any(RequestDto.class))).thenReturn(request);
        requestDto.setApplicantId(UUID.fromString("123e4567-e89b-12d3-a456-426614174002"));
        when(requestRepository.save(any(Request.class))).thenReturn(request);
        assertNotEquals(request.getApplicantId(), requestDto.getApplicantId());

    }

    @Test
    void addFail() {
        when(requestMapper.toEntity(any(RequestDto.class))).thenReturn(request);
        when(requestRepository.save(any(Request.class))).thenReturn(request);
        assertNotEquals(request.getApplicantId(), requestDto.getApplicantId());

    }

    @Test
    void findByIdSuccess() {
        when(requestRepository.findById(any(UUID.class))).thenReturn(Optional.of(request));
        when(requestMapper.toDto(any(Request.class))).thenReturn(requestDto);
        RequestDto byId = requestService.findById(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
        Assertions.assertNotNull(byId);

    }

    @Test
    void findByIdFail() {
        when(requestRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> requestService.findById(UUID.randomUUID()));

    }

}