package com.ashoppayment.mapper;

import com.ashoppayment.model.dto.CreateOrderRequestDto;
import com.ashoppayment.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "user.userId", target = "userId")
    CreateOrderRequestDto toDto(Order entity);
}
