package com.departament.requestservice.feign;

import com.departament.requestservice.dto.ReportDto;
import com.departament.requestservice.dto.SynthesizerRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(value = "synthesizer", url = "http://localhost:8078")
public interface SynthesizerClient {

    @GetMapping("/synthesizer")
    String sendRequestForAcceptedStatus(@RequestBody SynthesizerRequest synthesizerRequest);

}
