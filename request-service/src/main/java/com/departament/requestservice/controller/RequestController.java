package com.departament.requestservice.controller;

import com.departament.requestservice.dto.ReportDto;
import com.departament.requestservice.dto.RequestDto;
import com.departament.requestservice.dto.SynthesizerRequest;
import com.departament.requestservice.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<RequestDto> add(@RequestBody RequestDto requestDto) throws IOException {
        return ResponseEntity.ok(requestService.add(requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestDto> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(requestService.findById(id));
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<ReportDto> reportByApplicantId(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(requestService.reportByApplicantId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        requestService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<RequestDto> update(@RequestBody RequestDto requestDto) {
        return ResponseEntity.ok(requestService.update(requestDto));
    }

    @PostMapping("/synthesizer")
    public ResponseEntity<?> sendRequestForAcceptedStatus(@RequestBody SynthesizerRequest synthesizerRequest) {
        return ResponseEntity.ok(requestService.sendRequestForAcceptedStatus(synthesizerRequest));
    }

}
