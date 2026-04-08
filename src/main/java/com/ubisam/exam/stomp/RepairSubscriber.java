package com.ubisam.exam.stomp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ubisam.exam.api.repairs.RepairRepository;
import com.ubisam.exam.domain.Repair;

import io.u2ware.common.stomp.client.WebsocketStompClient;
import io.u2ware.common.stomp.client.WebsocketStompClientHandler;
import io.u2ware.common.stomp.client.config.WebsocketStompProperties;

@Component
public class RepairSubscriber implements WebsocketStompClientHandler{

    protected @Autowired RepairRepository nodeRepository;
    protected @Autowired ObjectMapper mapper;
    protected @Autowired WebsocketStompProperties properties;

    @Override
    public String getDestination() {
        return properties.getSubscriptions().get("repairs");
    }

 
    @Override
    public void handleFrame(WebsocketStompClient client, JsonNode message) {
        System.out.println("Received: " + message);
        JsonNode payloadNode = message.get("payload");
        if (payloadNode != null && payloadNode.has("Received Message")) {
            return;
        }

        Long timestamp = message.get("timestamp").asLong();
        String principal = message.get("principal").asText();
        String payload = payloadNode.toString();
        Repair e = new Repair();
        e.setPrincipal(principal);
        e.setTimestamp(timestamp);
        e.setState(payload);
        nodeRepository.save(e);

        ObjectNode send = mapper.createObjectNode();
        send.put("Received Message", "수리를 완료했어요");
        client.send("/app/repairs", send);
    }
}
