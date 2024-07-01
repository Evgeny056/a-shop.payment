package com.ashoppayment.messaging.listener;

import com.ashoppayment.model.dto.CreateOrderRequestDto;
import org.springframework.messaging.MessagingException;

public interface MessageListener {
    void listenMessage(CreateOrderRequestDto createOrderRequestDto) throws MessagingException;
}
