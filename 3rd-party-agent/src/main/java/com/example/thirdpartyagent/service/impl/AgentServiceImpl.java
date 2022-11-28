package com.example.thirdpartyagent.service.impl;

import com.example.thirdpartyagent.dto.ReportDto;
import com.example.thirdpartyagent.mapper.ReportMapper;
import com.example.thirdpartyagent.model.Report;
import com.example.thirdpartyagent.repo.ReportRepository;
import com.example.thirdpartyagent.service.AgentService;
import com.example.thirdpartyagent.util.ThirdPartyAgentServiceMock;
import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

import static com.example.thirdpartyagent.util.StringToObjectParser.parseStringToObject;

@Service
@Slf4j
public class AgentServiceImpl implements AgentService {

    private final WireMockServer wireMockServer = new WireMockServer(8082);
    private final ThirdPartyAgentServiceMock agentServiceMock;
    private final ReportRepository repository;
    private final ReportMapper mapper;

    public AgentServiceImpl(ThirdPartyAgentServiceMock agentServiceMock,
                            ReportRepository repository, ReportMapper mapper) {
        this.agentServiceMock = agentServiceMock;
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ReportDto reportByApplicantId(UUID applicantId) throws IOException {
        String reportDtoAsString = agentServiceMock.setup(wireMockServer).getResponse().getBody();
        ReportDto reportDto = parseStringToObject(reportDtoAsString);
        Report savedReport = repository.save(mapper.toEntity(reportDto));
        return mapper.toDto(savedReport);
    }
}
