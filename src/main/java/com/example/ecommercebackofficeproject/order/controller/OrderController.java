package com.example.ecommercebackofficeproject.order.controller;

import com.example.ecommercebackofficeproject.auth.dto.SessionAdminDto;
import com.example.ecommercebackofficeproject.order.dto.request.CancelOrderRequestDto;
import com.example.ecommercebackofficeproject.order.dto.request.CreateOrderRequestDto;
import com.example.ecommercebackofficeproject.order.dto.request.GetOrderRequestParamDto;
import com.example.ecommercebackofficeproject.order.dto.request.UpdateOrderRequestDto;
import com.example.ecommercebackofficeproject.order.dto.response.*;
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

    /**
     * 새로운 주문을 생성합니다.
     *
     * @param sessionAdminDto 세션에 저장된 로그인 관리자 정보
     * @param request         주문 생성 요청 데이터
     * @return 생성된 주문 정보
     */
    @PostMapping
    public ResponseEntity<CreateOrderResponseDto> createOrder(@RequestAttribute(name = "loginUser") SessionAdminDto sessionAdminDto,
                                                              @Valid @RequestBody CreateOrderRequestDto request) {
        CreateOrderResponseDto result = orderService.createOrder(sessionAdminDto.getAdminId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 특정 주문의 상세 정보를 조회합니다.
     *
     * @param sessionAdminDto 세션에 저장된 로그인 관리자 정보
     * @param orderId         조회할 주문 ID
     * @return 주문 상세 정보
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<GetOneOrderResponseDto> getOneOrder(@RequestAttribute(name = "loginUser") SessionAdminDto sessionAdminDto,
                                                              @PathVariable Long orderId) {
        GetOneOrderResponseDto result = orderService.getOneOrder(sessionAdminDto.getAdminId(), orderId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 주문 목록을 조회합니다.
     * (필터링 조건 및 페이징 정보 포함 가능)
     *
     * @param requestParam 조회 조건 (상태, 기간 등)
     * @return 주문 목록 조회 결과
     */
    @GetMapping
    public ResponseEntity<GetListOrderResponseDto> getListOrder(@ModelAttribute GetOrderRequestParamDto requestParam) {
        GetListOrderResponseDto results = orderService.getListOrder(requestParam);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    /**
     * 주문 상태를 변경합니다.
     * (예: PREPARING → SHIPPING → DELIVERED)
     *
     * @param sessionAdminDto 세션에 저장된 로그인 관리자 정보
     * @param orderId         상태를 변경할 주문 ID
     * @param request         변경할 상태 정보
     * @return 변경된 주문 정보
     */
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<UpdateOrderResponseDto> updateOrder(@RequestAttribute(name = "loginUser") SessionAdminDto sessionAdminDto,
                                                              @PathVariable Long orderId,
                                                              @Valid @RequestBody UpdateOrderRequestDto request) {
        UpdateOrderResponseDto result = orderService.updateOrder(sessionAdminDto.getAdminId(), orderId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 주문을 취소합니다.
     *
     * @param sessionAdminDto 세션에 저장된 로그인 관리자 정보
     * @param orderId         취소할 주문 ID
     * @param request         주문 취소 사유 및 정보
     * @return 취소된 주문 정보
     */
    @PatchMapping("/{orderId}")
    public ResponseEntity<CancelOrderResponseDto> cancelOrder(@RequestAttribute(name = "loginUser") SessionAdminDto sessionAdminDto,
                                                              @PathVariable Long orderId,
                                                              @Valid @RequestBody CancelOrderRequestDto request) {
        CancelOrderResponseDto result = orderService.cancelOrder(orderId, request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
