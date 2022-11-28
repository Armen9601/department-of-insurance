package com.example.thirdpartyagent.controller;

import com.example.thirdpartyagent.dto.ReportDto;
import com.example.thirdpartyagent.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/agent")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    @GetMapping("/{id}")
    public ResponseEntity<ReportDto> findById(@PathVariable("id") UUID id) throws IOException {
        return ResponseEntity.ok(agentService.findByApplicantId(id));
    }

}
