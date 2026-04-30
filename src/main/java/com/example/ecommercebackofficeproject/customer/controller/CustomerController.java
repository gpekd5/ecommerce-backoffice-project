package com.example.ecommercebackofficeproject.customer.controller;

import com.example.ecommercebackofficeproject.customer.dto.request.UpdateCustomerRequestDto;
import com.example.ecommercebackofficeproject.customer.dto.request.UpdateCustomerStatusRequestDto;
import com.example.ecommercebackofficeproject.customer.dto.response.GetCustomerDetailResponseDto;
import com.example.ecommercebackofficeproject.customer.dto.response.GetCustomerPageResponseDto;
import com.example.ecommercebackofficeproject.customer.dto.request.GetCustomerRequestDto;
import com.example.ecommercebackofficeproject.customer.dto.response.UpdateCustomerResponseDto;
import com.example.ecommercebackofficeproject.customer.dto.response.UpdateCustomerStatusResponseDto;
import com.example.ecommercebackofficeproject.customer.service.CustomerService;
import com.example.ecommercebackofficeproject.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 고객 관리 API를 처리하는 컨트롤러.
 *
 * 고객 목록 조회, 상세 조회, 정보 수정, 상태 변경, 삭제 기능을 제공한다.
 */
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /**
     * 고객 목록을 조회한다.
     *
     * 검색 키워드, 고객 상태, 페이지 번호, 페이지 크기,
     * 정렬 기준, 정렬 방향을 요청 파라미터로 전달받아 고객 목록을 조회한다.
     * 응답에는 고객 기본 정보와 총 주문 수, 총 구매 금액, 페이징 정보가 포함된다.
     *
     * @param request 고객 목록 조회 조건을 담은 요청 DTO
     * @return 고객 목록 및 페이징 정보를 담은 응답
     */
    @GetMapping("/customers")
    public ResponseEntity<ApiResponse<GetCustomerPageResponseDto>> getCustomers(
            @ModelAttribute GetCustomerRequestDto request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        HttpStatus.OK,
                        "고객 목록 조회를 하였습니다.",
                        customerService.getCustomers(request)
                ));
    }

    /**
     * 특정 고객의 상세 정보를 조회한다.
     *
     * 고객 ID를 기준으로 고객의 기본 정보, 총 주문 수,
     * 총 구매 금액을 조회한다.
     *
     * @param customerId 조회할 고객 ID
     * @return 고객 상세 정보를 담은 응답
     */
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<ApiResponse<GetCustomerDetailResponseDto>> getCustomer(
            @PathVariable Long customerId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        HttpStatus.OK,
                        "고객 상세 조회를 하였습니다.",
                        customerService.getCustomer(customerId)
                ));
    }

    /**
     * 고객 정보를 수정한다.
     *
     * 고객 ID를 기준으로 고객의 이름, 이메일, 전화번호를 수정한다.
     * 요청 Body에 대한 유효성 검증을 수행하며,
     * 수정 완료 후 변경된 고객 정보를 응답한다.
     *
     * @param customerId 수정할 고객 ID
     * @param request 고객 정보 수정 요청 DTO
     * @return 수정된 고객 정보를 담은 응답
     */
    @PatchMapping("/customers/{customerId}")
    public ResponseEntity<ApiResponse<UpdateCustomerResponseDto>> updateCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody UpdateCustomerRequestDto request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        HttpStatus.OK,
                        "고객 정보 수정을 완료하였습니다.",
                        customerService.updateCustomer(customerId, request)
                ));
    }

    /**
     * 고객 상태를 변경한다.
     *
     * 고객 ID를 기준으로 고객 상태를 변경한다.
     * 상태값은 ACTIVE, INACTIVE, SUSPENDED 중 하나여야 한다.
     * 변경 완료 후 변경된 고객 상태 정보를 응답한다.
     *
     * @param customerId 상태를 변경할 고객 ID
     * @param request 고객 상태 변경 요청 DTO
     * @return 변경된 고객 상태 정보를 담은 응답
     */
    @PatchMapping("/customers/{customerId}/status")
    public ResponseEntity<ApiResponse<UpdateCustomerStatusResponseDto>> updateCustomerStatus(
            @PathVariable Long customerId,
            @Valid @RequestBody UpdateCustomerStatusRequestDto request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        HttpStatus.OK,
                        "고객 상태 변경을 완료하였습니다.",
                        customerService.updateCustomerStatus(customerId, request)
                ));
    }

    /**
     * 고객 정보를 삭제 처리한다.
     *
     * 고객 ID를 기준으로 고객 정보를 삭제한다.
     * 실제 데이터를 즉시 삭제하지 않고 deletedAt 값을 설정하는
     * 논리 삭제 방식으로 처리한다.
     *
     * @param customerId 삭제할 고객 ID
     * @return 삭제 처리 완료 응답
     */
    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(
            @PathVariable Long customerId
    ) {
        customerService.deleteCustomer(customerId);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(
                        HttpStatus.OK,
                        "고객 정보 삭제가 되었습니다.",
                        null
                ));
    }
}