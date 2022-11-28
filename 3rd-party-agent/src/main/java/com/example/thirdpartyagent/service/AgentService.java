package com.example.thirdpartyagent.service;

import com.example.thirdpartyagent.dto.ReportDto;

import java.io.IOException;
import java.util.UUID;

public interface AgentService {

 ReportDto findByApplicantId(UUID applicantId) throws IOException;

}
