package com.example.ecommercebackofficeproject.order.service;

import com.example.ecommercebackofficeproject.order.dto.CreateOrderRequest;
import com.example.ecommercebackofficeproject.order.dto.CreateOrderResponse;
import com.example.ecommercebackofficeproject.order.entity.Order;
import com.example.ecommercebackofficeproject.order.repository.OrderRepository;
import com.example.ecommercebackofficeproject.product.entity.Product;
import com.example.ecommercebackofficeproject.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CreateOrderResponse createOrder(Long adminId, CreateOrderRequest request) {
        // todo - 단종 / 품절 / 재고 부족 / 고객 없음 / 상품 없음 / 401 / 403 처리
        Product product = productRepository.findById(request.getProductId()).orElseThrow(
                () -> new IllegalStateException("상품을 찾을 수 없습니다.") // todo - 상품 없음 에러 처리
        );
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(
                () -> new IllegalStateException("고객을 찾을 수 없습니다.") // todo - 고객 없음 에러 처리
        );

        Order order = Order.builder()
                .orderNumber(createOrderNumber())
                .customer(customer)
                .product(product)
                .quantity(request.getQuantity())
                .createdByAdminId(adminId)
                .build();

        orderRepository.save(order);

        return CreateOrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .customerId(order.customer.getId())
                .productId(order.getProduct().getProductId())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .orderedAt(order.getCreatedAt())
                .createdByAdminId(order.getCreatedByAdminId())
                .build();
    }



    // 주문번호 생성 메서드 (ex. 20260424-001)
    private String createOrderNumber() {
        LocalDate today = LocalDate.now();

        String orderNumber = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 해당하는 날짜의 주문 건수 계산
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();
        long count = orderRepository.countByCreatedAtBetween(start, end);

        orderNumber += "-" + String.format("%03d", count + 1);

        return orderNumber;
    }

    // todo - 401 처리 메서드 작성

    // todo - 403 처리 메서드 작성

    // todo - 고객 404 처리 메서드 작성

    // todo - 상품 404 처리 메서드 작성

}
