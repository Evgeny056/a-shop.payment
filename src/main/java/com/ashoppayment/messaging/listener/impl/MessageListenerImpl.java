package com.ashoppayment.messaging.listener.impl;

import com.ashoppayment.messaging.listener.MessageListener;
import com.ashoppayment.model.dto.CreateOrderRequestDto;
import com.ashoppayment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class MessageListenerImpl implements MessageListener {

    private final PaymentService paymentService;
/*
    @Override
    @KafkaListener(topics = "new_orders", groupId = "payment-group")
    public void listenMessage(String message) {
        try {
            CreateOrderRequestDto createOrderRequestDto = objectMapper.readValue(message, CreateOrderRequestDto.class);
            log.info("Received New Order Request: {}", message);
            paymentService.payment(createOrderRequestDto);

        } catch (JsonProcessingException e) {
            System.out.println("Error parsing message: " + e.getMessage());
        }
    }*/

    private final String topic = "new_orders"; // Топик, на который подписывается потребитель

    @KafkaListener(topics = topic, groupId = "payment-group")
    public void listenMessage(CreateOrderRequestDto createOrderRequestDto) {
            log.info("Зашли в слушателя. Уже прогресс!!!");
            paymentService.payment(createOrderRequestDto);
            log.info("Проверить изменения в БД");
    }
}
