package com.ashoppayment.messaging.listener.impl;

import com.ashoppayment.model.dto.CreateOrderRequestDto;
import com.ashoppayment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class MessageListenerImpl /*implements MessageListener*/ {

    private final PaymentService paymentService;
    private final String topic = "new_orders";

    @KafkaListener(
            topics = topic,
            groupId = "payment-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listenMessage(@Header(KafkaHeaders.RECEIVED_KEY) String key, CreateOrderRequestDto createOrderRequestDto) {
            log.info("Received order message from orders service with key: {}", key);
            paymentService.payment(createOrderRequestDto);
    }
}
