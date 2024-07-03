package com.ashoppayment.messaging.producer.impl;

import com.ashoppayment.messaging.producer.MessageProducer;
import com.ashoppayment.model.dto.CreateOrderRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducerImpl implements MessageProducer {

    private String newPaymentTopic = "payed_orders";
    private String changeOrdersStatusTopic = "change_orders_status";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void sendChangeStatusOrder(CreateOrderRequestDto createOrderRequestDto) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(createOrderRequestDto);
            log.info("Change order status {}:", jsonMessage);
            kafkaTemplate.send(changeOrdersStatusTopic, jsonMessage);
        } catch (JsonProcessingException e) {
            log.error("Error while converting object to JSON: {}", e.getMessage());
        }
    }

    @Override
    public void sendToShipping(CreateOrderRequestDto createOrderRequestDto) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(createOrderRequestDto);
            log.info("Sending message to {}: {}", newPaymentTopic, jsonMessage);
            kafkaTemplate.send(newPaymentTopic, jsonMessage);
        } catch (JsonProcessingException e) {
            log.error("Error while converting object to JSON: {}", e.getMessage());
        }
    }
}
