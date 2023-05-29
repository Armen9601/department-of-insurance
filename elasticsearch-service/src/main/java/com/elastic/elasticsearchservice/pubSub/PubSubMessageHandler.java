package com.elastic.elasticsearchservice.pubSub;

import com.elastic.elasticsearchservice.dto.NestedObject;
import com.elastic.elasticsearchservice.dto.RequestDto;
import com.elastic.elasticsearchservice.service.ElasticsearchService;
import com.elastic.elasticsearchservice.util.ObjectParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.LinkedHashMap;

@Slf4j
@Component
public class PubSubMessageHandler {

    private final ElasticsearchService elasticsearchService;
    private final ObjectParser objectParser;
    private final ObjectMapper objectMapper;

    public PubSubMessageHandler(ElasticsearchService elasticsearchService, ObjectParser objectParser, ObjectMapper objectMapper) {
        this.elasticsearchService = elasticsearchService;
        this.objectParser = objectParser;
        this.objectMapper = objectMapper;
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
                new PubSubInboundChannelAdapter(pubSubTemplate, "elastic-service-sub");
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.MANUAL);

        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "pubsubInputChannel")
    public MessageHandler messageReceiver() {
        return message -> {
            String json = new String((byte[]) message.getPayload());
            log.info("Message arrived for indexing! Payload: " + json);
            try {
                Object o = objectMapper.readValue(json, Object.class);
                if (((LinkedHashMap) o).get("request") == null){
                    RequestDto requestDto = objectParser.parseStringToRequest(json);
                    elasticsearchService.indexDocument("request",requestDto.getId().toString(),requestDto);
                }else {
                    NestedObject doc = objectParser.parseStringToObject(json);
                    elasticsearchService.indexDocument("nested", doc.getUuid().toString(), doc);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            BasicAcknowledgeablePubsubMessage originalMessage =
                    message.getHeaders().get(GcpPubSubHeaders.ORIGINAL_MESSAGE, BasicAcknowledgeablePubsubMessage.class);
            originalMessage.ack();
        };
    }

}
