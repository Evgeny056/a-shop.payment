package com.ashoppayment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateOrderRequestDto {
    private long orderId;
    private long userId;
    private String status;
}
