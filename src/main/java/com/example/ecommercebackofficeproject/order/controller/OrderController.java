package com.example.ecommercebackofficeproject.order.controller;

import com.example.ecommercebackofficeproject.order.dto.request.CreateOrderRequestDto;
import com.example.ecommercebackofficeproject.order.dto.response.CreateOrderResponseDto;
import com.example.ecommercebackofficeproject.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CreateOrderResponseDto> createOrder(@SessionAttribute(name = "loginUser"/**수정 예정*/) SessionUserDto sessionUser,
                                                              @Valid @RequestBody CreateOrderRequestDto request) {
        CreateOrderResponseDto result = orderService.createOrder(sessionUser.getId()/**메서드 명 수정 예정*/, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
