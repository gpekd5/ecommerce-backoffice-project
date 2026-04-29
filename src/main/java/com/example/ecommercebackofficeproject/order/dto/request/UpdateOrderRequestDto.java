package com.example.ecommercebackofficeproject.order.dto.request;

import com.example.ecommercebackofficeproject.order.type.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateOrderRequestDto {
    @NotNull(message = "orderStatus는 필수 입력값입니다.")
    private String orderStatus;

}
