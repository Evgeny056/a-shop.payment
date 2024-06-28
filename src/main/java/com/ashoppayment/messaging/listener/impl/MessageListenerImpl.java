package com.ashoppayment.messaging.listener.impl;

import com.ashoppayment.messaging.listener.MessageListener;
import com.ashoppayment.model.dto.CreateOrderRequestDto;
import com.ashoppayment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class MessageListenerImpl implements MessageListener {

    private final PaymentService paymentService;

    @Override
    @KafkaListener(topics = "new_orders", groupId = "payment-group")
    public void listenMessage(String message) {
            log.info("Received New Order Request: {}", message);
            paymentService.payment(message);
    }
}
