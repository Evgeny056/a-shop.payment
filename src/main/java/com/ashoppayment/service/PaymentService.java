package com.ashoppayment.service;

import com.ashoppayment.exception.OrderNotFoundException;
import com.ashoppayment.mapper.OrderMapper;
import com.ashoppayment.messaging.producer.MessageProducer;
import com.ashoppayment.model.dto.CreateOrderRequestDto;
import com.ashoppayment.model.entity.Order;
import com.ashoppayment.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final MessageProducer messageProducer;
    private final ObjectMapper objectMapper;

    public void payment(String message) {

        CreateOrderRequestDto createOrderRequestDto = null;
        try {
            createOrderRequestDto = objectMapper.readValue(message, CreateOrderRequestDto.class);
            if (createOrderRequestDto.getStatus().equalsIgnoreCase("CREATED")) {

                Order order = orderRepository.findById(createOrderRequestDto.getOrderId())
                        .orElseThrow(() -> new OrderNotFoundException("Order not found"));

                //логика оплаты, полагаем, что оплата прошла успешно
                order.setStatus("PAID");
                orderRepository.save(order);

                messageProducer.sendToShipping(orderMapper.toCreateOrderRequestDto(order));
                messageProducer.sendToOrders(orderMapper.toCreateOrderRequestDto(order));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json caput");
        }
    }
}
