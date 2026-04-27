package com.example.ecommercebackofficeproject.order.service;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import com.example.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.example.ecommercebackofficeproject.customer.entity.Customer;
import com.example.ecommercebackofficeproject.customer.repository.CustomerRepository;
import com.example.ecommercebackofficeproject.order.dto.request.GetOrderRequestParamDto;
import com.example.ecommercebackofficeproject.order.dto.request.CreateOrderRequestDto;
import com.example.ecommercebackofficeproject.order.dto.response.*;
import com.example.ecommercebackofficeproject.order.entity.Order;
import com.example.ecommercebackofficeproject.order.repository.OrderRepository;
import com.example.ecommercebackofficeproject.product.entity.Product;
import com.example.ecommercebackofficeproject.product.repository.ProductRepository;
import com.example.ecommercebackofficeproject.product.type.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;

    /**
     * 주문을 생성하는 메서드 입니다.
     * */

    /**
     * 주문을 생성하는 메서드 입니다.
     *
     */

    @Transactional
    public CreateOrderResponseDto createOrder(Long adminId, CreateOrderRequestDto request) {
        // todo - 단종 / 품절 / 재고 부족 / 고객 없음 / 상품 없음 / 403 처리

        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new IllegalStateException("adf") // 추후 추가 예정
        );
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
                .admin(admin)
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
                .createdByAdminId(order.getAdmin().getId())
                .build();
    }

    /**
     * 특정 주문을 조회하는 메서드입니다.
     *
     */

    @Transactional(readOnly = true)
    public GetOneOrderResponseDto getOneOrder(Long adminId, Long orderId) {
        // todo - 주문 404 / 403
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalStateException("주문을 찾을 수 없습니다.") // todo - 주문 404 에러 처리
        );

        return GetOneOrderResponseDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .customerName(order.getCustomer().getName())
                .customerEmail(order.getCustomer().getEmail())
                .productName(order.getProduct().getProductName())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus())
                .createdByAdminName(order.getAdmin().getName())
                .createdByAdminEmail(order.getAdmin().getEmail())
                .createdByAdminRole(order.getAdmin().getRole())
                .orderedAt(order.getCreatedAt())
                .build();

    }

    @Transactional(readOnly = true)
    public GetListOrderResponseDto getListOrder(Long adminId, GetOrderRequestParamDto request) {
        // 1. 키워드 ("" -> null)
        String keyword = request.getKeyword();
        if (keyword != null && keyword.isBlank()) {
            keyword = null;
        }

        // page & size 처리
        int page = request.getPage() != null ? request.getPage() : 1;
        int size = request.getSize() != null ? request.getSize() : 10;


        // 정렬 처리
        String sortBy = request.getSortBy() != null ? request.getSortBy() : "orderedAt";
        String sortDirection = request.getSortDirection() != null ? request.getSortDirection() : "DESC";

        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));

        // 조회
        Page<Order> orderPage = orderRepository.searchOrders(
                keyword,
                request.getOrderStatus(),
                pageable
        );

        // DTO 변환
        List<GetListOrderItemResponseDto> itemLists = orderPage.getContent().stream()
                .map(order -> GetListOrderItemResponseDto.builder()
                        .id(order.getId())
                        .customerName(order.getCustomer().getName())
                        .productName(order.getProduct().getProductName())
                        .quantity(order.getQuantity())
                        .totalPrice(order.getTotalPrice())
                        .orderedAt(order.getCreatedAt())
                        .orderStatus(order.getOrderStatus())
                        .createdByAdminName(order.getAdmin().getName())
                        .build()
                ).toList();

        PageResponseDto pageResponse = PageResponseDto.builder()
                .currentPage(page)
                .size(orderPage.getSize())
                .totalElements(orderPage.getTotalElements())
                .totalPages(orderPage.getTotalPages())
                .build();

        return GetListOrderResponseDto.builder()
                .itemList(itemLists)
                .pageResponse(pageResponse)
                .build();
    }


    /**
     * 주문 번호를 생성하는 메서드 입니다.
     * 생성 예시 : 20260424-001
     *
     */
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
