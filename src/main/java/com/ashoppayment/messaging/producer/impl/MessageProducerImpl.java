package com.ashoppayment.messaging.producer.impl;

import com.ashoppayment.messaging.producer.MessageProducer;
import com.ashoppayment.model.dto.CreateOrderRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducerImpl implements MessageProducer {

    private String newPaymentTopic = "payed_orders";
//    private String newOrdersTopic = "new_orders";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

/*    @Override
    public void sendToOrders(CreateOrderRequestDto createOrderRequestDto) {
        CompletableFuture<SendResult<String, CreateOrderRequestDto>> future = kafkaTemplate.send(
                newOrdersTopic, createOrderRequestDto
        );
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message to topic {} with offset {}", newOrdersTopic, result.getRecordMetadata().offset());
            } else {
                log.error("Unable to send message to topic {} due to : {}", newOrdersTopic, ex.getMessage());
            }
        });
    }*/

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
