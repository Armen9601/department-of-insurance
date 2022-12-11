package com.departament.requestservice.feign;

import com.departament.requestservice.dto.ReportDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(value = "agent", url = "http://localhost:8082")
public interface AgentClient {
    @GetMapping("/agent/{id}")
    ReportDto reportByApplicantId(@PathVariable("id") UUID id);

}
