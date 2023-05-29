package com.example.sintezatorservice.service.impl;

import com.example.sintezatorservice.dto.NestedObject;
import com.example.sintezatorservice.dto.RequestDto;
import com.example.sintezatorservice.dto.SynthesizerRequest;
import com.example.sintezatorservice.feignClient.ElasticFeignClient;
import com.example.sintezatorservice.gateway.PubsubOutboundGateway;
import com.example.sintezatorservice.mapper.DetailsMapper;
import com.example.sintezatorservice.mapper.SynthesizerMapper;
import com.example.sintezatorservice.model.Status;
import com.example.sintezatorservice.model.Synthesizer;
import com.example.sintezatorservice.repo.SynthesizerRepository;
import com.example.sintezatorservice.service.DetailsService;
import com.example.sintezatorservice.service.SynthesizerService;
import com.example.sintezatorservice.util.ObjectParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SynthesizerServiceImpl implements SynthesizerService {

    private final ObjectParser objectParser;
    private final SynthesizerRepository synthesizerRepository;
    private final SynthesizerMapper mapper;
    private final ElasticFeignClient elasticFeignClient;
    private final ObjectMapper objectMapper;
    private final PubSubTemplate pubSubTemplate;
    private final DetailsService detailsService;
    private final DetailsMapper detailsMapper;

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
                    if (request.getRequestDto().getCreatedDate().isBefore(LocalDateTime.now())) {
                        synthesizer.setStatus(Status.ACCEPTED);
                    } else if (request.getRequestDto().getApplicantId() == null) {
                        synthesizer.setStatus(Status.SUSPENDED);
                    } else {
                        synthesizer.setStatus(Status.REJECTED);
                    }
                    synthesizer.setDetails(detailsMapper.toEntity(detailsService.findAll()));
                    Mono<Synthesizer> save = synthesizerRepository.save(synthesizer);
                    save.subscribe(result -> {
                        try {
                            String index = "synthesizer";
                            System.out.println(index);
//                            elasticFeignClient.indexDocument(index,result.getId().toString(),mapper.toDto(result));
//                            List<String> requests = elasticFeignClient.searchDoc("request", "id", result.getRequestId().toString());
//                            RequestDto requestDto = objectMapper.readValue(requests.get(0), RequestDto.class);
//                            requestDto.setSynthesizer(mapper.toDto(result));
                            NestedObject build = NestedObject.builder()
                                    .uuid(UUID.randomUUID())
//                                    .request(requestDto)
                                    .build();
                            String value = objectParser.parseObjectToJson(build);
                            byte[] nested = value.getBytes();
//                            pubSubTemplate.publish("elastic-service", nested);
//                            elasticFeignClient.indexDocument("nested", result.getRequestId().toString(), build);
                            String completed = objectParser.parseObjectToJson(mapper.toDto(result));
                            byte[] payload = completed.getBytes();
                            pubSubTemplate.publish("sintezator-service", payload);
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
