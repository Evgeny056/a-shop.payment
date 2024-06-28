package com.ashoppayment.mapper;

import com.ashoppayment.model.dto.CreateOrderRequestDto;
import com.ashoppayment.model.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    CreateOrderRequestDto toCreateOrderRequestDto(Order order);
    Order toOrder(CreateOrderRequestDto createOrderRequestDto);
}
