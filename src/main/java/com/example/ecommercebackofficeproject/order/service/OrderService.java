package com.example.ecommercebackofficeproject.order.service;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import com.example.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.example.ecommercebackofficeproject.customer.entity.Customer;
import com.example.ecommercebackofficeproject.customer.repository.CustomerRepository;
import com.example.ecommercebackofficeproject.order.dto.request.CancelOrderRequestDto;
import com.example.ecommercebackofficeproject.order.dto.request.GetOrderRequestParamDto;
import com.example.ecommercebackofficeproject.order.dto.request.CreateOrderRequestDto;
import com.example.ecommercebackofficeproject.order.dto.request.UpdateOrderRequestDto;
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
     * 주문 생성 API
     * <p>
     * 주문을 생성하는 메서드입니다.
     * - 관리자, 상품, 고객 존재 여부를 검증합니다.
     * - 상품 상태(단종, 품절) 및 재고를 확인합니다.
     * - 주문 번호를 생성하고 주문을 저장합니다.
     *
     * @param adminId 로그인한 관리자 ID
     * @param request 주문 생성 요청 데이터 (상품 ID, 고객 ID, 수량 등)
     * @return 생성된 주문 정보
     */
    @Transactional
    public CreateOrderResponseDto createOrder(Long adminId, CreateOrderRequestDto request) {
        // todo - 단종 / 품절 / 재고 부족 / 고객 없음 / 상품 없음 / 403 처리
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new IllegalStateException("로그인한 관리자를 찾을 수 없습니다.")
        );
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

        // 상품 재고 차감
        product.updateStock(-1 * order.getQuantity());

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
     * 특정 주문 단건 조회 API
     * <p>
     * 주문 ID를 기반으로 주문 상세 정보를 조회합니다.
     * - 관리자 존재 여부 검증
     * - 주문 존재 여부 검증
     *
     * @param adminId 로그인한 관리자 ID
     * @param orderId 조회할 주문 ID
     * @return 주문 상세 정보
     */
    @Transactional(readOnly = true)
    public GetOneOrderResponseDto getOneOrder(Long adminId, Long orderId) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new IllegalStateException("로그인한 관리자를 찾을 수 없습니다.")
        );

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

    /**
     * 주문 목록 조회 API
     * <p>
     * 주문 목록을 조회하는 메서드입니다.
     * - 키워드 검색 (고객명, 상품명 등)
     * - 주문 상태 필터링
     * - 페이징 및 정렬 처리
     *
     * @param request 주문 조회 조건 (키워드, 상태, 페이지, 정렬 등)
     * @return 주문 목록 및 페이지 정보
     */
    @Transactional(readOnly = true)
    public GetListOrderResponseDto getListOrder(GetOrderRequestParamDto request) {
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
     * 주문 상태 변경 API
     * 주문의 상태를 변경하는 메서드입니다.
     * - 현재 상태에서 변경 가능한 상태인지 검증합니다.
     *
     * @param orderId 상태를 변경할 주문 ID
     * @param request 변경할 주문 상태 정보
     * @return 변경된 주문 상태 정보
     */
    @Transactional
    public UpdateOrderResponseDto updateOrder(Long orderId, UpdateOrderRequestDto request) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalStateException("주문을 찾을 수 없습니다.") // todo - custom exception
        );
        if (!order.getOrderStatus().canChangeTo(request.getOrderStatus())) {
            throw new IllegalArgumentException("현재 상태에서는 해당 상태로 변경할 수 없습니다."); // todo - custom exception
        }

        order.changeOrderStatus(request.getOrderStatus());
        return UpdateOrderResponseDto.builder()
                .id(order.getId())
                .orderStatus(order.getOrderStatus())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    /**
     * 주문 취소 API
     * <p>
     * 주문을 취소하고 취소 사유를 저장합니다.
     * - 주문 상태를 취소 상태로 변경합니다.
     * - 재고 복구 로직이 포함될 수 있습니다.
     *
     * @param orderId 취소할 주문 ID
     * @param request 주문 취소 요청 데이터 (취소 사유)
     * @return 취소된 주문 정보 및 재고 복구 결과
     */
    @Transactional
    public CancelOrderResponseDto cancelOrder(Long orderId, CancelOrderRequestDto request) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalStateException("나중에 처리 예정") // todo - custom exception
        );
        order.cancel(request.getOrderCancelReason());
        
        // 재고 복구
        Product product = order.getProduct();
        product.updateStock(order.getQuantity());

        return CancelOrderResponseDto.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderStatus(order.getOrderStatus())
                .cancelReason(order.getOrderCancelReason())
                .canceledAt(order.getDeletedAt())
                .productId(order.getProduct().getProductId())
                .productName(order.getProduct().getProductName())
                .restoredQuantity(order.getQuantity())
                .currentStock(order.getProduct().getStock())
                .productStatus(order.getProduct().getStatus()).build();
    }

    /**
     * 주문 번호 생성 메서드
     * <p>
     * 현재 날짜 기준으로 주문 번호를 생성합니다.
     * 형식: yyyyMMdd-XXX (예: 20260424-001)
     * - 하루 주문 건수를 기반으로 순번을 부여합니다.
     *
     * @return 생성된 주문 번호
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
