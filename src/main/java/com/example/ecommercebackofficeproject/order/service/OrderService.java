package com.example.ecommercebackofficeproject.order.service;

import com.example.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.example.ecommercebackofficeproject.customer.entity.Customer;
import com.example.ecommercebackofficeproject.customer.repository.CustomerRepository;
import com.example.ecommercebackofficeproject.order.dto.request.CreateOrderRequestDto;
import com.example.ecommercebackofficeproject.order.dto.response.CreateOrderResponseDto;
import com.example.ecommercebackofficeproject.order.dto.response.GetOneOrderResponseDto;
import com.example.ecommercebackofficeproject.order.entity.Order;
import com.example.ecommercebackofficeproject.order.repository.OrderRepository;
import com.example.ecommercebackofficeproject.product.entity.Product;
import com.example.ecommercebackofficeproject.product.repository.ProductRepository;
import com.example.ecommercebackofficeproject.product.type.ProductStatus;
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
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public CreateOrderResponseDto createOrder(Long adminId, CreateOrderRequestDto request) {
        // todo - 단종 / 품절 / 재고 부족 / 고객 없음 / 상품 없음 / 403 처리

        // todo - 403 처리
        Product product = productRepository.findById(request.getProductId()).orElseThrow(
                () -> new IllegalStateException("상품을 찾을 수 없습니다.") // todo - 상품 없음 에러 처리
        );
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(
                () -> new IllegalStateException("고객을 찾을 수 없습니다.") // todo - 고객 없음 에러 처리
        );
        if (product.getStatus() == ProductStatus.DISCONTINUED) { // todo - 단종된 상품 에러 처리
            throw new IllegalArgumentException("단종된 상품은 주문할 수 없습니다.");
        } else if (product.getStatus() == ProductStatus.SOLD_OUT) { // todo - 품절된 상품 에러 처리
            throw new IllegalArgumentException("품절된 상품은 주문할 수 없습니다.");
        }
        if (request.getQuantity() > product.getStock()) {
            throw new IllegalArgumentException("상품 재고가 부족합니다."); // todo - 재고 부족 에러 처리
        }


        Order order = Order.builder()
                .orderNumber(createOrderNumber())
                .customer(customer)
                .product(product)
                .quantity(request.getQuantity())
                .createdByAdminId(adminId)
                .build();

        // todo - 상품 재고 update

        orderRepository.save(order);

        return CreateOrderResponseDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .customerId(order.getCustomer().getId())
                .productId(order.getProduct().getProductId())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus())
                .orderedAt(order.getCreatedAt())
                .createdByAdminId(order.getCreatedByAdminId())
                .build();
    }

//    @Transactional
//    public GetOneOrderResponseDto getOneOrder(Long adminId, Long orderId) {
//        // todo - 주문 404 / 403
//        Order order = orderRepository.findById(orderId).orElseThrow(
//                () -> new IllegalStateException("주문을 찾을 수 없습니다.") // todo - 주문 404 에러 처리
//        );
//
//
//
//    }


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

}
