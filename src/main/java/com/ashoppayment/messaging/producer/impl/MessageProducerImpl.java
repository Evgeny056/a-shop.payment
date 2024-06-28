package com.ashoppayment.messaging.producer.impl;

import com.ashoppayment.messaging.producer.MessageProducer;
import com.ashoppayment.model.dto.CreateOrderRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducerImpl implements MessageProducer {

    private String newPaymentTopic = "payed_orders";
    private String newOrdersTopic = "new_orders";

    private final KafkaTemplate<String, CreateOrderRequestDto> kafkaTemplate;

    @Override
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
    }

    @Override
    public void sendToShipping(CreateOrderRequestDto createOrderRequestDto) {
        CompletableFuture<SendResult<String, CreateOrderRequestDto>> future = kafkaTemplate.send(
                newPaymentTopic, createOrderRequestDto
        );

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message to topic {} with offset {}", newPaymentTopic, result.getRecordMetadata().offset());
            } else {
                log.error("Unable to send message to topic {} due to : {}", newPaymentTopic, ex.getMessage());
            }
        });
    }
}
