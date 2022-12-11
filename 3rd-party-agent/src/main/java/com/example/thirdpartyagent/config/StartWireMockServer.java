package com.example.thirdpartyagent.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Component
public class StartWireMockServer {

    @Value("${wiremock.port}")
    private int port;
    @Value("${wiremock.mapping.path}")
    private String mappingPath;

    @PostConstruct
    public void methos() throws IOException {
        WireMockServer wireMockServer = new WireMockServer(port);
        StubMapping stubMapping = StubMapping
                .buildFrom(new String(Files.readAllBytes(Paths.get(mappingPath))));
        wireMockServer.start();
        log.info("wiremock started in 15500");
        wireMockServer.addStubMapping(stubMapping);
    }

}
