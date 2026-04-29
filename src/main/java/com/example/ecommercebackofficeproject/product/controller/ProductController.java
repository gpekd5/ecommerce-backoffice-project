package com.example.ecommercebackofficeproject.product.controller;

import com.example.ecommercebackofficeproject.auth.dto.SessionAdminDto;
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
 */
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 새로운 상품을 등록합니다.
     * @param dto           상품 등록 요청 정보
     * @param sessionAdmin  세션에 저장된 로그인 유저 정보
     * @return 등록된 상품 정보
     */
    @PostMapping("/products")
    public ResponseEntity<CreateProductResponseDto> saveProduct(@Valid @RequestBody ProductRequestDto dto, @RequestAttribute(name="loginUser") SessionAdminDto sessionAdmin) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(dto, sessionAdmin.getAdminId()));
    }

    /**
     * 상품 목록을 페이징 처리하여 조회합니다.
     * 키워드(상품명), 카테고리, 상품 상태에 따른 필터링 기능을 제공하며,
     * 페이징 및 정렬 조건을 적용하여 결과를 반환합니다.
     * @param keyword  검색할 상품명 키워드 (선택 사항)
     * @param category 조회할 상품 카테고리 (선택 사항)
     * @param status   조회할 상품 상태 (선택 사항)
     * @param pageable 페이징 및 정렬 정보 (Spring Data JPA 자동 생성)
     * @return 페이징 처리된 상품 응답 DTO 목록을 담은 ResponseEntity
     */
    @GetMapping("/products")
    public ResponseEntity<GetProductPageResponseDto> getProductList(@RequestParam(required = false) String keyword, @RequestParam(required = false) ProductCategory category, @RequestParam(required = false) ProductStatus status, @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(productService.getProductList(keyword, category, status, pageable));
    }

    /**
     * 특정 상품의 상세 정보를 조회합니다.
     * @param productId 조회할 상품의 고유 식별자(ID)
     * @return 조회된 상품 상세 정보 DTO를 담은 ResponseEntity
     */
    @GetMapping("/products/{productId}")
    public ResponseEntity<GetProductResponseDto> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    /**
     * 특정 상품의 기본 정보(상품명, 카테고리, 가격 등)를 수정합니다.
     * @param productId    수정할 상품의 고유 식별자(ID)
     * @param dto          수정할 상품 정보 요청 데이터 (상품명, 카테고리, 가격 등)
     * @param sessionAdmin 세션에서 가져온 관리자 정보 (수정 권한 확인용)  해당 로직을 글로벌로 구현 할지 고민중
     * @return 수정이 완료된 상품의 상세 정보 DTO
     */
    @PatchMapping("/products/{productId}")
    public ResponseEntity<ProductResponseDto> updateProductInfo(@PathVariable Long productId, @Valid @RequestBody ProductUpdateInfoDto dto, @RequestAttribute(name="loginUser") SessionAdminDto sessionAdmin) {
        return ResponseEntity.ok(productService.updateProductInfo(productId, dto));
    }

    /**
     * 특정 상품의 판매 상태(판매 중, 품절, 단종 등)를 수동으로 변경합니다.
     * @param productId    상태를 변경할 상품의 고유 식별자(ID)
     * @param dto          변경할 상태 정보를 담은 요청 데이터
     * @param sessionAdmin 세션에서 가져온 관리자 정보 (수정 권한 확인용)  해당 로직을 글로벌로 구현 할지 고민중
     * @return 상태 변경이 완료된 상품의 상세 정보 DTO
     */
    @PatchMapping("/products/{productId}/status")
    public ResponseEntity<ProductResponseDto> updateProductStatus(@PathVariable Long productId, @Valid @RequestBody ProductUpdateStatusDto dto, @RequestAttribute(name="loginUser") SessionAdminDto sessionAdmin) {
        return ResponseEntity.ok(productService.updateProductStatus(productId, dto));
    }

    /**
     * 특정 상품을 삭제 처리합니다.
     * 실제 데이터베이스에서 레코드를 삭제하지 않고, 삭제 일시(deletedAt)를 기록하여
     * 논리적으로 삭제(Soft Delete) 상태로 변경합니다.
     * @param productId 삭제할 상품의 고유 식별자(ID)
     * @return 성공 시 응답 본문이 없는 ResponseEntity (204 No Content)
     */
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
