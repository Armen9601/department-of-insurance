package com.example.thirdpartyagent.service.impl;

import com.example.thirdpartyagent.dto.ReportDto;
import com.example.thirdpartyagent.mapper.ReportMapper;
import com.example.thirdpartyagent.model.Report;
//import com.example.thirdpartyagent.repo.ReportRepository;
import com.example.thirdpartyagent.service.AgentService;
import com.example.thirdpartyagent.util.StringToObjectParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class AgentServiceImpl implements AgentService {

//    private final ReportRepository repository;
    private final ReportMapper mapper;

    @Value("${wiremock.server.url}")
    private String wiremockServerUrl;

    public AgentServiceImpl(ReportMapper mapper) {
//        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ReportDto reportByApplicantId(UUID applicantId) {
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpGet httpGet = new HttpGet(wiremockServerUrl);
//        try {
//            CloseableHttpResponse response = client.execute(httpGet);
//            HttpEntity entity = response.getEntity();
//            String responseString = EntityUtils.toString(entity, "UTF-8");
//            ReportDto reportDto = StringToObjectParser.parseStringToObject(responseString);
////            Report savedReport = repository.save(mapper.toEntity(reportDto));
//            return reportDto;
//        } catch (IOException | ParseException e) {
//            e.printStackTrace();
//        }
        return new ReportDto(UUID.randomUUID(),UUID.randomUUID(),"name");
    }

}
