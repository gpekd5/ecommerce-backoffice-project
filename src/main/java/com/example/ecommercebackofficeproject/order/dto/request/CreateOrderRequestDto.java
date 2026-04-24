package com.example.ecommercebackofficeproject.order.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateOrderRequestDto {

    @NotNull(message = "회원 정보가 누락되었습니다.")
    private Long customerId;

    @NotNull(message = "상품 정보가 누락되었습니다.")
    private Long productId;

    @NotNull(message = "구매 수량이 누락되었습니다.")
    @Min(value = 1, message = "주문 수량은 1개 이상이어야 합니다.")
    private int quantity;
}
