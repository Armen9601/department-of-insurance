package com.departament.requestservice.service.impl;

import com.departament.requestservice.feign.AgentClient;
import com.departament.requestservice.dto.ReportDto;
import com.departament.requestservice.dto.RequestDto;
import com.departament.requestservice.entity.Request;
import com.departament.requestservice.exception.EntityNotFoundException;
import com.departament.requestservice.mapper.RequestMapper;
import com.departament.requestservice.repo.RequestRepository;
import com.departament.requestservice.service.RequestService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Log4j2
public class RequestServiceImpl implements RequestService {

    private final RequestRepository repository;

    private final RequestMapper mapper;

    private final AgentClient agentClient;


    public RequestServiceImpl(RequestRepository repository, RequestMapper mapper,
                              AgentClient agentClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.agentClient = agentClient;
    }

    @Override
    public RequestDto add(RequestDto requestDto) {
        requestDto.setCreatedDate(LocalDateTime.now());
        Request savedRequest = repository.save(mapper.toEntity(requestDto));
        log.info("request is added: id: {}", savedRequest.getId());
        return mapper.toDto(savedRequest);
    }

    @Override
    public RequestDto findById(UUID id) {
        return mapper.toDto(repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public RequestDto update(RequestDto requestDto) {
        Request updated = repository.save(mapper.toEntity(requestDto));
        log.info("request is updated: id: {}", updated.getId());
        return mapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public ReportDto reportByApplicantId(UUID id) {
        return agentClient.reportByApplicantId(id);
    }
}
