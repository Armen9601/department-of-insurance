package com.departament.requestservice.feign;

import com.departament.requestservice.dto.SynthesizerRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "synthesizer", url = "${synthesizer.feign.url}")
public interface SynthesizerClient {

    @PostMapping("/synthesizer")
    String sendRequestForAcceptedStatus(@RequestBody SynthesizerRequest synthesizerRequest);

}
