package com.example.ecommercebackofficeproject.product.controller;

import com.example.ecommercebackofficeproject.auth.dto.SessionAdminDto;
import com.example.ecommercebackofficeproject.product.dto.request.ProductRequestDto;
import com.example.ecommercebackofficeproject.product.dto.response.CreateProductResponseDto;
import com.example.ecommercebackofficeproject.product.dto.response.GetProductPageResponseDto;
import com.example.ecommercebackofficeproject.product.dto.response.GetProductResponseDto;
import com.example.ecommercebackofficeproject.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     * @param dto 상품 등록 요청 정보
     * @param sessionAdmin 세션에 저장된 로그인 유저 정보
     * @return 등록된 상품 정보
     */
    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> saveProduct(@Valid @RequestBody ProductRequestDto dto, @SessionAttribute(name="loginUser") SessionAdminDto sessionAdmin) {

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
    public ResponseEntity<Page<GetProductPageResponseDto>> getProductList(@RequestParam(required = false) String keyword, @RequestParam(required = false) String category, @RequestParam(required = false) String status, Pageable pageable) {
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
}
