package com.ashoppayment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequestDto {
    private long orderId;
    private long userId;
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateOrderRequestDto that = (CreateOrderRequestDto) o;
        return orderId == that.orderId && userId == that.userId && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        int result = Long.hashCode(orderId);
        result = 31 * result + Long.hashCode(userId);
        result = 31 * result + Objects.hashCode(status);
        return result;
    }

}
