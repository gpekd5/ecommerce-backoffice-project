package com.example.ecommercebackofficeproject.order.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CancelOrderRequestDto {
    @NotNull(message = "주문 취소 사유는 필수입니다.")
    private String orderCancelReason;
}
