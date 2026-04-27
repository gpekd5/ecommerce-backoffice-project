package com.example.ecommercebackofficeproject.customer.controller;

import com.example.ecommercebackofficeproject.customer.dto.request.UpdateCustomerRequestDto;
import com.example.ecommercebackofficeproject.customer.dto.request.UpdateCustomerStatusRequestDto;
import com.example.ecommercebackofficeproject.customer.dto.response.GetCustomerDetailResponseDto;
import com.example.ecommercebackofficeproject.customer.dto.response.GetCustomerPageResponseDto;
import com.example.ecommercebackofficeproject.customer.dto.request.GetCustomerRequestDto;
import com.example.ecommercebackofficeproject.customer.dto.response.UpdateCustomerResponseDto;
import com.example.ecommercebackofficeproject.customer.dto.response.UpdateCustomerStatusResponseDto;
import com.example.ecommercebackofficeproject.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /*
     * 고객 목록 조회 API
     *
     * 검색 키워드, 상태 필터, 페이지 번호, 페이지 크기,
     * 정렬 기준, 정렬 방향을 요청 파라미터로 받아 고객 목록을 조회한다.
     *
     * 응답에는 고객 기본 정보와 함께 총 주문 수, 총 구매 금액,
     * 페이징 정보가 포함된다.
     */
    @GetMapping("/customers")
    public ResponseEntity<GetCustomerPageResponseDto> getCustomers(
            @ModelAttribute GetCustomerRequestDto request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomers(request));
    }

    /*
     * 고객 상세 조회 API
     *
     * 고객 ID를 PathVariable로 받아 특정 고객의 상세 정보를 조회한다.
     *
     * 응답에는 고객 기본 정보와 함께 해당 고객의 총 주문 수,
     * 총 구매 금액이 포함된다.
     */
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<GetCustomerDetailResponseDto> getCustomer(
            @PathVariable Long customerId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomer(customerId));
    }

    /*
     * 고객 정보 수정 API
     *
     * 고객 ID를 기준으로 고객의 이름, 이메일, 전화번호를 수정한다.
     *
     * 요청 Body는 유효성 검증을 수행하며,
     * 수정 완료 후 변경된 고객 정보를 응답한다.
     */
    @PatchMapping("/customers/{customerId}")
    public ResponseEntity<UpdateCustomerResponseDto> updateCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody UpdateCustomerRequestDto request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomer(customerId, request));
    }

    /*
     * 고객 상태 변경 API
     *
     * 고객 ID를 기준으로 고객 상태를 변경한다.
     *
     * 상태값은 ACTIVE, INACTIVE, SUSPENDED 중 하나여야 하며,
     * 변경 완료 후 변경된 상태 정보를 응답한다.
     */
    @PatchMapping("/customers/{customerId}/status")
    public ResponseEntity<UpdateCustomerStatusResponseDto> updateCustomerStatus(
            @PathVariable Long customerId,
            @Valid @RequestBody UpdateCustomerStatusRequestDto request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomerStatus(customerId, request));
    }

    /*
     * 고객 삭제 API
     *
     * 고객 ID를 기준으로 고객을 삭제 처리한다.
     *
     * 실제 데이터를 즉시 삭제하지 않고,
     * deletedAt 값을 설정하는 논리 삭제 방식으로 처리한다.
     */
    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable Long customerId
    ) {
        customerService.deleteCustomer(customerId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
