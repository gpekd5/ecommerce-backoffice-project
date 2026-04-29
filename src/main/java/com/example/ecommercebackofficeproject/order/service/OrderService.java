package com.example.ecommercebackofficeproject.order.service;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import com.example.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.example.ecommercebackofficeproject.admin.type.AdminStatus;
import com.example.ecommercebackofficeproject.customer.entity.Customer;
import com.example.ecommercebackofficeproject.customer.repository.CustomerRepository;
import com.example.ecommercebackofficeproject.global.exception.BadRequestException;
import com.example.ecommercebackofficeproject.global.exception.ForbiddenException;
import com.example.ecommercebackofficeproject.global.exception.NotFoundException;
import com.example.ecommercebackofficeproject.global.exception.UnauthorizedException;
import com.example.ecommercebackofficeproject.order.dto.request.CancelOrderRequestDto;
import com.example.ecommercebackofficeproject.order.dto.request.GetOrderRequestParamDto;
import com.example.ecommercebackofficeproject.order.dto.request.CreateOrderRequestDto;
import com.example.ecommercebackofficeproject.order.dto.request.UpdateOrderRequestDto;
import com.example.ecommercebackofficeproject.order.dto.response.*;
import com.example.ecommercebackofficeproject.order.entity.Order;
import com.example.ecommercebackofficeproject.order.repository.OrderRepository;
import com.example.ecommercebackofficeproject.order.type.OrderStatus;
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
        Admin admin = validateAdmin(adminId);
        Product product = validateProduct(request.getProductId());
        Customer customer = validateCustomer(request.getCustomerId());

        if (product.getStatus() == ProductStatus.DISCONTINUED) {
            throw new BadRequestException("단종된 상품은 주문할 수 없습니다.");
        } else if (product.getStatus() == ProductStatus.SOLD_OUT) {
            throw new BadRequestException("품절된 상품은 주문할 수 없습니다.");
        }
        if (request.getQuantity() > product.getStock()) {
            throw new BadRequestException("상품 재고가 부족합니다.");
        }
        if (request.getQuantity() > 0) {
            throw new BadRequestException("주문 수량은 1개 이상이어야 합니다.");
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
        validateAdmin(adminId);
        Order order = validateOrder(orderId);

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
        // 페이지 검증 / 처리
        int page = validatePage(request.getPage());
        // 페이지 당 개수 검증 / 처리
        int size = validateSize(request.getSize());
        // 키워드 처리
        String keyword = normalizeKeyword(request.getKeyword());
        // 정렬 기준 검증 / 처리
        String sortBy = validateSortBy(request.getSortBy());
        // 정렬 순서 검증 / 처리
        Sort.Direction sortDirection = validateSortDirection(request.getSortDirection());
        // 주문 상태 검증 / 처리
        OrderStatus orderStatus = validateOrderStatus(request.getOrderStatus());

        Pageable pageable = PageRequest.of(
                page - 1,
                size,
                Sort.by(sortDirection, sortBy)
        );

        Page<Order> orderPage = orderRepository.searchOrders(
                keyword,
                orderStatus,
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
    public UpdateOrderResponseDto updateOrder(Long adminId, Long orderId, UpdateOrderRequestDto request) {
        validateAdmin(adminId);
        Order order = validateOrder(orderId);
        OrderStatus status = validateOrderStatus(request.getOrderStatus());
        order.changeOrderStatus(status);

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
        Order order = validateOrder(orderId);

        if (order.getOrderStatus() == OrderStatus.CANCELED) {
            throw new BadRequestException("이미 취소된 주문입니다.");
        } else if (order.getOrderStatus() != OrderStatus.PREPARING) {
            throw new BadRequestException("준비 중인 상태의 주문만 취소할 수 있습니다.");
        }

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

    /**
     * 관리자 ID로 관리자를 조회하고, 로그인 및 활성 상태를 검증합니다.
     *
     * @param adminId 관리자 ID
     * @return 검증된 관리자 엔티티
     * @throws UnauthorizedException 관리자가 존재하지 않는 경우 (로그인 안됨)
     * @throws ForbiddenException    관리자가 비활성 상태인 경우 (권한 없음)
     */
    private Admin validateAdmin(Long adminId) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new UnauthorizedException("로그인한 관리자를 찾을 수 없습니다.")
        );
        if (admin.getStatus() != AdminStatus.ACTIVE) {
            throw new ForbiddenException("접근 권한이 없습니다.");
        }
        return admin;
    }

    /**
     * 상품 ID로 상품을 조회합니다.
     *
     * @param productId 상품 ID
     * @return 조회된 상품 엔티티
     * @throws NotFoundException 상품이 존재하지 않는 경우
     */
    private Product validateProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new NotFoundException("상품을 찾을 수 없습니다.")
        );
    }

    /**
     * 고객 ID로 고객을 조회합니다.
     *
     * @param customerId 고객 ID
     * @return 조회된 고객 엔티티
     * @throws NotFoundException 고객이 존재하지 않는 경우
     */
    private Customer validateCustomer(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(
                () -> new NotFoundException("고객을 찾을 수 없습니다.")
        );
    }

    /**
     * 주문 ID로 주문을 조회합니다.
     *
     * @param orderId 주문 ID
     * @return 조회된 주문 엔티티
     * @throws NotFoundException 주문이 존재하지 않는 경우
     */
    private Order validateOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("주문을 찾을 수 없습니다.")
        );
    }

    /**
     * 정렬 기준 값을 검증하고, 실제 엔티티 필드명으로 변환합니다.
     *
     * <p>지원하는 정렬 기준:</p>
     * <ul>
     *     <li>orderedAt → createdAt</li>
     *     <li>totalPrice</li>
     *     <li>orderStatus</li>
     * </ul>
     *
     * @param sortBy 클라이언트에서 전달된 정렬 기준
     * @return 엔티티 필드명으로 변환된 정렬 기준
     * @throws BadRequestException 지원하지 않는 정렬 기준인 경우
     */
    private String validateSortBy(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return "createdAt";
        }

        return switch (sortBy) {
            case "orderedAt" -> "createdAt";
            case "totalPrice" -> "totalPrice";
            case "orderStatus" -> "orderStatus";
            default -> throw new BadRequestException("지원하지 않는 정렬 기준입니다.");
        };
    }

    /**
     * 정렬 순서를 검증하고 {@link Sort.Direction}으로 변환합니다.
     *
     * <p>지원하는 값:</p>
     * <ul>
     *     <li>ASC</li>
     *     <li>DESC</li>
     * </ul>
     *
     * @param sortDirection 클라이언트에서 전달된 정렬 순서
     * @return Sort.Direction (ASC 또는 DESC)
     * @throws BadRequestException 지원하지 않는 정렬 순서인 경우
     */
    private Sort.Direction validateSortDirection(String sortDirection) {
        if (sortDirection == null || sortDirection.isBlank()) {
            return Sort.Direction.DESC;
        }

        return switch (sortDirection.toUpperCase()) {
            case "ASC" -> Sort.Direction.ASC;
            case "DESC" -> Sort.Direction.DESC;
            default -> throw new BadRequestException("지원하지 않는 정렬 순서입니다.");
        };
    }

    /**
     * 주문 상태 문자열을 검증하고 {@link OrderStatus} enum으로 변환합니다.
     *
     * <p>null 또는 공백일 경우 필터 미적용으로 간주하여 null을 반환합니다.</p>
     *
     * @param orderStatus 클라이언트에서 전달된 주문 상태 문자열
     * @return 변환된 OrderStatus 또는 null
     * @throws BadRequestException 지원하지 않는 주문 상태인 경우
     */
    private OrderStatus validateOrderStatus(String orderStatus) {
        if (orderStatus == null || orderStatus.isBlank()) {
            return null;
        }

        try {
            return OrderStatus.valueOf(orderStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("지원하지 않는 주문 상태입니다.");
        }
    }

    /**
     * 검색 키워드를 정규화합니다.
     *
     * <p>공백 문자열은 null로 변환하여 검색 조건에서 제외합니다.</p>
     *
     * @param keyword 검색 키워드
     * @return 정규화된 키워드 또는 null
     */
    private String normalizeKeyword(String keyword) {
        if (keyword != null && keyword.isBlank()) {
            return null;
        }

        return keyword;
    }

    /**
     * 페이지 번호를 검증하고 기본값을 설정합니다.
     *
     * <p>null인 경우 기본값 1을 반환합니다.</p>
     *
     * @param page 요청된 페이지 번호
     * @return 검증된 페이지 번호
     * @throws BadRequestException 페이지 번호가 1보다 작은 경우
     */
    private int validatePage(Integer page) {
        if (page == null) {
            return 1;
        }
        if (page < 1) {
            throw new BadRequestException("페이지 번호는 1 이상이어야 합니다.");
        }
        return page;
    }

    /**
     * 페이지 당 조회 개수를 검증하고 기본값을 설정합니다.
     *
     * <p>null인 경우 기본값 10을 반환합니다.</p>
     *
     * @param size 요청된 페이지 크기
     * @return 검증된 페이지 크기
     * @throws BadRequestException 페이지 크기가 1보다 작은 경우
     */
    private int validateSize(Integer size) {
        if (size == null) {
            return 10;
        }
        if (size < 1) {
            throw new BadRequestException("페이지당 개수는 1 이상이어야 합니다.");
        }
        return size;
    }

}
