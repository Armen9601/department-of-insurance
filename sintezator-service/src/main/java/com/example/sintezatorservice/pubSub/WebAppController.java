package com.example.sintezatorservice.pubSub;

import com.example.sintezatorservice.gateway.PubsubOutboundGateway;
import com.example.sintezatorservice.model.Synthesizer;
import com.example.sintezatorservice.util.ObjectParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
public class WebAppController {

    private final PubsubOutboundGateway messagingGateway;
    private final ObjectParser objectParser;

    @PostMapping("/publishMessage")
    public RedirectView publishMessage(@RequestBody Synthesizer message) throws JsonProcessingException {
        String s = objectParser.parseObjectToJson(message);
        messagingGateway.sendToPubsub(s);
        return new RedirectView("/");
    }
}