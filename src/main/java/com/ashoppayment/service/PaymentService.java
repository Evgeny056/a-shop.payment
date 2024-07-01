package com.ashoppayment.service;

import com.ashoppayment.exception.OrderNotFoundException;
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

    public void payment(CreateOrderRequestDto createOrderRequestDto) {
        if (createOrderRequestDto.getStatus().equalsIgnoreCase("CREATED")) {

            Order order = orderRepository.findById(createOrderRequestDto.getOrderId())
                    .orElseThrow(() -> new OrderNotFoundException("Order not found"));

            //логика оплаты, полагаем, что оплата прошла успешно
            order.setStatus("PAID");
            orderRepository.save(order);
        }

    }
}
