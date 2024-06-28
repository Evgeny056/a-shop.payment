package com.ashoppayment.messaging.listener;

import com.ashoppayment.model.dto.CreateOrderRequestDto;

public interface MessageListener {
    void listenMessage(String message);
}
