package com.example.sintezatorservice.service.impl;

import com.example.sintezatorservice.dto.ReportDto;
import com.example.sintezatorservice.dto.RequestDto;
import com.example.sintezatorservice.dto.SynthesizerDto;
import com.example.sintezatorservice.dto.SynthesizerRequest;
import com.example.sintezatorservice.gateway.PubsubOutboundGateway;
import com.example.sintezatorservice.mapper.SynthesizerMapper;
import com.example.sintezatorservice.model.Status;
import com.example.sintezatorservice.model.Synthesizer;
import com.example.sintezatorservice.repo.SynthesizerRepository;
import com.example.sintezatorservice.util.ObjectParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
class SynthesizerServiceImplTest {

    @Mock
    private PubsubOutboundGateway gateway;
    @Mock
    private ObjectParser objectParser;
    @InjectMocks
    private SynthesizerRepository synthesizerRepository;
    @Mock
    private SynthesizerMapper mapper;
    @InjectMocks
    private SynthesizerServiceImpl service;

    @Test
    public void testProcessingRequest() throws JsonProcessingException {
        Synthesizer synthesizer = Synthesizer.builder()
                .requestId(UUID.fromString("8a27c73e-76d2-4376-82ae-1cec166170f6"))
                .reportId(UUID.fromString("8a27c73e-76d2-4376-82ae-1cec166170f5"))
                .status(Status.ACCEPTED)
                .build();

        SynthesizerRequest synthesizerRequest = new SynthesizerRequest();
        synthesizerRequest.setRequestDto(new RequestDto());
        synthesizerRequest.getRequestDto().setId(UUID.fromString("8a27c73e-76d2-4376-82ae-1cec166170f6"));
        synthesizerRequest.getRequestDto().setCreatedDate(LocalDateTime.now().minusMinutes(5));
        synthesizerRequest.setReportDto(new ReportDto());
        synthesizerRequest.getReportDto().setId(UUID.fromString("8a27c73e-76d2-4376-82ae-1cec166170f5"));

        when(synthesizerRepository.save(any(Synthesizer.class))).thenReturn(Mono.just(synthesizer));
        when(objectParser.parseObjectToJson(any())).thenReturn("{}");
        when(mapper.toDto((Synthesizer) any())).thenReturn(new SynthesizerDto());

        service.processingRequest(synthesizerRequest);

        verify(synthesizerRepository, times(1)).save(any(Synthesizer.class));
        verify(objectParser, times(1)).parseObjectToJson(any());
        verify(mapper, times(1)).toDto((Synthesizer) any());
        verify(gateway, times(1)).sendToPubsub(any());
    }
}