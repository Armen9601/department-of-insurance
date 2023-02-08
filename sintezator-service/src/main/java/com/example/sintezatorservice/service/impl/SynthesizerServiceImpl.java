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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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

    void processingRequest(SynthesizerRequest synthesizerRequest) {
        Mono.just(synthesizerRequest).publishOn(Schedulers.boundedElastic())
                .doOnNext(request -> {
                    Synthesizer synthesizer = Synthesizer.builder()
                            .requestId(request.getRequestDto().getId())
                            .reportId(request.getReportDto().getId())
                            .build();
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    int j = 2 / 0;
                    if (request.getRequestDto().getCreatedDate().isBefore(LocalDateTime.now())) {
                        synthesizer.setStatus(Status.ACCEPTED);
                    } else if (request.getRequestDto().getApplicantId() == null) {
                        synthesizer.setStatus(Status.SUSPENDED);
                    } else {
                        synthesizer.setStatus(Status.REJECTED);
                    }
                    Mono<Synthesizer> save = synthesizerRepository.save(synthesizer);
                    save.subscribe(result -> {
                        try {
                            String completed = objectParser.parseObjectToJson(mapper.toDto(result));
                            gateway.sendToPubsub(completed);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    });

                })
                .subscribe(
                        success -> System.out.println("Synthesizer sent:"),
                        error -> System.out.println("ERROR: " + error)
                        );


    }
}
