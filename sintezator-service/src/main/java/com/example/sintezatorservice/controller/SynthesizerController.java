package com.example.sintezatorservice.controller;

import com.example.sintezatorservice.dto.SynthesizerRequest;
import com.example.sintezatorservice.service.SynthesizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/synthesizer")
@RequiredArgsConstructor
public class SynthesizerController {

    private final SynthesizerService synthesizerService;

    @PostMapping
    public ResponseEntity<?> acceptedRequestForProcessing(@RequestBody SynthesizerRequest synthesizerRequest) {
        synthesizerService.acceptedRequestForProcessing(synthesizerRequest);
        return ResponseEntity.ok("request accepted for processing");
    }

}
