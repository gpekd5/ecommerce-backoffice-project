package com.example.ecommercebackofficeproject.product.controller;

import com.example.ecommercebackofficeproject.product.dto.request.ProductRequestDto;
import com.example.ecommercebackofficeproject.product.dto.response.ProductResponseDto;
import com.example.ecommercebackofficeproject.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
     * @param sessionUser 세션에 저장된 로그인 유저 정보
     * @return 등록된 상품 정보
     */
    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> saveProduct(@Valid @RequestBody ProductRequestDto dto, @SessionAttribute(name="loginUser") SessionUserDto sessionUser) {

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(dto, sessionUser.getAdminId()));
    }
}
