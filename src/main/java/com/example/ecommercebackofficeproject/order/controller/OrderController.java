package com.example.ecommercebackofficeproject.order.controller;

import com.example.ecommercebackofficeproject.order.dto.CreateOrderRequest;
import com.example.ecommercebackofficeproject.order.dto.CreateOrderResponse;
import com.example.ecommercebackofficeproject.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public CreateOrderResponse createOrder(@SessionAttribute(name = "loginUser"/**수정 예정*/) SessionUser sessionUser,
                                           @Valid @RequestBody CreateOrderRequest request){
        CreateOrderResponse result = orderService.createOrder(sessionUser.getId()/**메서드 명 수정 예정*/, request);
    }
}
