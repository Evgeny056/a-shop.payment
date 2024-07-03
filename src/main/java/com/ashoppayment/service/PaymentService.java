package com.ashoppayment.service;

import com.ashoppayment.exception.OrderNotFoundException;
import com.ashoppayment.mapper.OrderMapper;
import com.ashoppayment.messaging.producer.MessageProducer;
import com.ashoppayment.model.dto.CreateOrderRequestDto;
import com.ashoppayment.model.entity.Order;
import com.ashoppayment.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final OrderRepository orderRepository;
    private final MessageProducer messageProducer;
    private final OrderMapper orderMapper;

    public void payment(CreateOrderRequestDto createOrderRequestDto) {
        if (createOrderRequestDto.getStatus().equalsIgnoreCase("CREATED")) {

            Order order = orderRepository.findById(createOrderRequestDto.getOrderId())
                    .orElseThrow(() -> new OrderNotFoundException("Order not found"));

            order.setStatus("PAID");
            orderRepository.save(order);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            CreateOrderRequestDto dtoSend = orderMapper.INSTANCE.toDto(order);
            messageProducer.sendChangeStatusOrder(dtoSend);
            messageProducer.sendToShipping(dtoSend);
        }
    }
}
