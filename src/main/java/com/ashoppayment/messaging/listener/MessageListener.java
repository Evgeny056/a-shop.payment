package com.ashoppayment.messaging.listener;

import com.ashoppayment.model.dto.CreateOrderRequestDto;
import org.springframework.messaging.MessagingException;

public interface MessageListener {
    void listenMessage(String key, CreateOrderRequestDto createOrderRequestDto) throws MessagingException;
}
