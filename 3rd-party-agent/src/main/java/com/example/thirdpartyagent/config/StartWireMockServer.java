package com.example.thirdpartyagent.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        wireMockServer.addStubMapping(stubMapping);
    }

}
