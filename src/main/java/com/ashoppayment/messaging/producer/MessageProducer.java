package com.ashoppayment.messaging.producer;

import com.ashoppayment.model.dto.CreateOrderRequestDto;

public interface MessageProducer {
    void sendChangeStatusOrder(CreateOrderRequestDto createOrderRequestDto);
    void sendToShipping(CreateOrderRequestDto createOrderRequestDto);
}
