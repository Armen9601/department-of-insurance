package com.departament.requestservice.pubSub;

import com.departament.requestservice.entity.Synthesizer;
import com.departament.requestservice.repo.SynthesizerRepository;
import com.departament.requestservice.util.ObjectParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class PubSubMessageHandler {

    private final SynthesizerRepository synthesizerRepository;
    private final ObjectParser objectParser;

    public PubSubMessageHandler(SynthesizerRepository synthesizerRepository, ObjectParser objectParser) {
        this.synthesizerRepository = synthesizerRepository;
        this.objectParser = objectParser;
    }

    @Bean
    public MessageChannel pubsubInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
            @Qualifier("pubsubInputChannel") MessageChannel inputChannel,
            PubSubTemplate pubSubTemplate) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, "sintezator");
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.MANUAL);

        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "pubsubInputChannel")
    public MessageHandler messageReceiver() {
        return message -> {
            String synthesizerJson = new String((byte[]) message.getPayload());
            log.info("Message arrived! Payload: " + synthesizerJson);
            try {
                Synthesizer synthesizer = objectParser.parseStringToObject(synthesizerJson);
                synthesizerRepository.save(synthesizer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            BasicAcknowledgeablePubsubMessage originalMessage =
                    message.getHeaders().get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
            originalMessage.ack();
        };
    }

}
