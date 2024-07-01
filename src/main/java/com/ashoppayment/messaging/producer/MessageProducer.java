package com.ashoppayment.messaging.producer;

import com.ashoppayment.model.dto.CreateOrderRequestDto;

public interface MessageProducer {
//    void sendToOrders(CreateOrderRequestDto createOrderRequestDto);
    void sendToShipping(CreateOrderRequestDto createOrderRequestDto);
}
