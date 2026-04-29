package com.example.ecommercebackofficeproject.product.controller;

import com.example.ecommercebackofficeproject.auth.dto.SessionAdminDto;
import com.example.ecommercebackofficeproject.global.response.ApiResponse;
import com.example.ecommercebackofficeproject.product.dto.request.ProductRequestDto;
import com.example.ecommercebackofficeproject.product.dto.request.ProductUpdateInfoDto;
import com.example.ecommercebackofficeproject.product.dto.request.ProductUpdateStatusDto;
import com.example.ecommercebackofficeproject.product.dto.response.CreateProductResponseDto;
import com.example.ecommercebackofficeproject.product.dto.response.GetProductPageResponseDto;
import com.example.ecommercebackofficeproject.product.dto.response.GetProductResponseDto;
import com.example.ecommercebackofficeproject.product.dto.response.ProductResponseDto;
import com.example.ecommercebackofficeproject.product.service.ProductService;
import com.example.ecommercebackofficeproject.product.type.ProductCategory;
import com.example.ecommercebackofficeproject.product.type.ProductStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 상품 관리를 위한 API 컨트롤러입니다.
 * JWT 인증을 통해 관리자 권한을 확인하며, 상품의 등록/조회/수정/삭제 기능을 제공합니다.
 */
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 새로운 상품을 등록합니다.
     * @param dto           상품 등록 요청 정보
     * @param sessionAdmin  JWT 토큰 인증을 통해 추출된 로그인 관리자 정보
     * @return 등록된 상품 정보
     */
    @PostMapping("/products")
    public ResponseEntity<ApiResponse<CreateProductResponseDto>> saveProduct(@Valid @RequestBody ProductRequestDto dto, @RequestAttribute(name="loginUser") SessionAdminDto sessionAdmin) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(HttpStatus.CREATED, "신규 상품을 등록했습니다.", productService.saveProduct(dto, sessionAdmin.getAdminId())));
    }

    /**
     * 상품 목록을 페이징 처리하여 조회합니다.
     * 키워드(상품명), 카테고리, 상품 상태에 따른 필터링 기능을 제공합니다.
     * @param keyword  검색할 상품명 키워드 (선택 사항)
     * @param category 조회할 상품 카테고리 (선택 사항)
     * @param status   조회할 상품 상태 (선택 사항)
     * @param pageable 페이징 및 정렬 정보
     * @return 페이징 처리된 상품 응답 데이터
     */
    @GetMapping("/products")
    public ResponseEntity<ApiResponse<GetProductPageResponseDto>> getProductList(@RequestParam(required = false) String keyword, @RequestParam(required = false) ProductCategory category, @RequestParam(required = false) ProductStatus status, @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(HttpStatus.OK, "상품목록을 조회했습니다.", productService.getProductList(keyword, category, status, pageable)));
    }

    /**
     * 특정 상품의 상세 정보를 조회합니다.
     * @param productId 조회할 상품의 ID
     * @return 조회된 상품 상세 정보
     */
    @GetMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<GetProductResponseDto>> getProduct(@PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(HttpStatus.OK, "상품 상세정보를 조회했습니다.", productService.getProduct(productId)));
    }

    /**
     * 특정 상품의 기본 정보(상품명, 카테고리, 가격 등)를 수정합니다.
     * @param productId    수정할 상품의 ID
     * @param dto          수정할 상품 정보 요청 데이터
     * @param sessionAdmin 인증된 관리자 정보 (수정 권한 확인용)
     * @return 수정이 완료된 상품 상세 정보
     */
    @PatchMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProductInfo(@PathVariable Long productId, @Valid @RequestBody ProductUpdateInfoDto dto, @RequestAttribute(name="loginUser") SessionAdminDto sessionAdmin) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(HttpStatus.OK, "특정 상품의 기본정보를 수정했습니다.", productService.updateProductInfo(productId, dto)));
    }

    /**
     * 특정 상품의 판매 상태(판매 중, 품절, 단종 등)를 수동으로 변경합니다.
     * @param productId    상태를 변경할 상품의 ID
     * @param dto 상태 정보 (유효하지 않은 값이 들어올 경우 ProductStatus Enum 내에서 BadRequestException 발생)
     * @param sessionAdmin 인증된 관리자 정보 (수정 권한 확인용)
     * @return 상태 변경이 완료된 상품 상세 정보
     */
    @PatchMapping("/products/{productId}/status")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProductStatus(@PathVariable Long productId, @Valid @RequestBody ProductUpdateStatusDto dto, @RequestAttribute(name="loginUser") SessionAdminDto sessionAdmin) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(HttpStatus.OK, "판매상태를 변경했습니다.", productService.updateProductStatus(productId, dto)));
    }

    /**
     * 특정 상품을 논리적으로 삭제(Soft Delete)합니다.
     * 데이터베이스에서 레코드를 삭제하지 않고 deletedAt 일시를 기록합니다.
     * @param productId 삭제할 상품의 ID
     * @return 삭제 완료 메시지
     */
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(HttpStatus.OK, "상품 정보가 삭제되었습니다.", null));
    }
}
