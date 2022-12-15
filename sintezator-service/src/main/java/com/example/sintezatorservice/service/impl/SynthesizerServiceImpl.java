package com.example.sintezatorservice.service.impl;

import com.example.sintezatorservice.dto.SynthesizerRequest;
import com.example.sintezatorservice.gateway.PubsubOutboundGateway;
import com.example.sintezatorservice.mapper.SynthesizerMapper;
import com.example.sintezatorservice.model.Status;
import com.example.sintezatorservice.model.Synthesizer;
import com.example.sintezatorservice.repo.SynthesizerRepository;
import com.example.sintezatorservice.service.SynthesizerService;
import com.example.sintezatorservice.util.ObjectParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SynthesizerServiceImpl implements SynthesizerService {

    private final PubsubOutboundGateway gateway;
    private final ObjectParser objectParser;
    private final SynthesizerRepository synthesizerRepository;
    private final SynthesizerMapper mapper;

    @Override
    public void acceptedRequestForProcessing(SynthesizerRequest synthesizerRequest) {
        processingRequest(synthesizerRequest);
    }

    private void processingRequest(SynthesizerRequest synthesizerRequest) {
        Thread newThread = new Thread(() -> {
            Synthesizer synthesizer = Synthesizer.builder()
                    .requestId(synthesizerRequest.getRequestDto().getId())
                    .reportId(synthesizerRequest.getReportDto().getId())
                    .build();
            if (synthesizerRequest.getRequestDto().getCreatedDate().isBefore(LocalDateTime.now())) {
                synthesizer.setStatus(Status.ACCEPTED);
            } else if (synthesizerRequest.getRequestDto().getApplicantId() == null) {
                synthesizer.setStatus(Status.SUSPENDED);
            } else {
                synthesizer.setStatus(Status.REJECTED);
            }
            Synthesizer saved = synthesizerRepository.save(synthesizer);
            try {
                String completed = objectParser.parseObjectToJson(mapper.toDto(saved));
                gateway.sendToPubsub(completed);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        newThread.start();
    }
}
