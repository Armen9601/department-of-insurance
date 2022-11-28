package com.example.thirdpartyagent.util;

import com.example.thirdpartyagent.config.WireMockServerInitializerConfig;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

@Component
public class ThirdPartyAgentServiceMock extends WireMockServerInitializerConfig {

    public StubMapping setup(WireMockServer wireMockServer) throws IOException {
        return wireMockServer.stubFor(
                WireMock.put("http://localhost:8081/agent")
                        .willReturn(
                                WireMock.aResponse()
                                        .withStatus(HttpStatus.OK.value())
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(
                                                copyToString(
                                                        ThirdPartyAgentServiceMock.class
                                                                .getClassLoader()
                                                                .getResourceAsStream("payload/reportResponse.json"),
                                                        defaultCharset()))));
    }

}
