package com.example.ecommercebackofficeproject.order.controller;

import com.example.ecommercebackofficeproject.auth.dto.SessionAdminDto;
import com.example.ecommercebackofficeproject.customer.dto.response.GetCustomerResponseDto;
import com.example.ecommercebackofficeproject.order.dto.request.CreateOrderRequestDto;
import com.example.ecommercebackofficeproject.order.dto.request.GetOrderRequestParamDto;
import com.example.ecommercebackofficeproject.order.dto.response.CreateOrderResponseDto;
import com.example.ecommercebackofficeproject.order.dto.response.GetListOrderItemResponseDto;
import com.example.ecommercebackofficeproject.order.dto.response.GetListOrderResponseDto;
import com.example.ecommercebackofficeproject.order.dto.response.GetOneOrderResponseDto;
import com.example.ecommercebackofficeproject.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CreateOrderResponseDto> createOrder(@SessionAttribute(name = "loginUser") SessionAdminDto sessionAdminDto,
                                                              @Valid @RequestBody CreateOrderRequestDto request) {
        CreateOrderResponseDto result = orderService.createOrder(sessionAdminDto.getAdminId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<GetOneOrderResponseDto> getOneOrder(@SessionAttribute(name = "loginUser") SessionAdminDto sessionAdminDto, @PathVariable Long orderId) {
        GetOneOrderResponseDto result = orderService.getOneOrder(sessionAdminDto.getAdminId(), orderId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping
    public ResponseEntity<GetListOrderResponseDto> getListOrder(@SessionAttribute(name="loginUser")SessionAdminDto sessionAdminDto, GetOrderRequestParamDto requestParam){
        GetListOrderResponseDto results = orderService.getListOrder(sessionAdminDto.getAdminId(), requestParam);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }
}
