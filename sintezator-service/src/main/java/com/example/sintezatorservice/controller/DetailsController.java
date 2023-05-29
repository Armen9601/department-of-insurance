package com.example.sintezatorservice.controller;

import com.example.sintezatorservice.dto.DetailsDto;
import com.example.sintezatorservice.dto.SynthesizerRequest;
import com.example.sintezatorservice.model.Details;
import com.example.sintezatorservice.service.DetailsService;
import com.example.sintezatorservice.service.SynthesizerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/details")
@RequiredArgsConstructor
public class DetailsController {

    private final DetailsService detailsService;

    @PostMapping
    public ResponseEntity<?> acceptedRequestForProcessing(@RequestBody DetailsDto details) {
        detailsService.save(details);
        return ResponseEntity.ok("Details added");
    }

}
