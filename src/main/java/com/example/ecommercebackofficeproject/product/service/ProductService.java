package com.example.ecommercebackofficeproject.product.service;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import com.example.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.example.ecommercebackofficeproject.product.dto.request.ProductRequestDto;
import com.example.ecommercebackofficeproject.product.dto.response.ProductResponseDto;
import com.example.ecommercebackofficeproject.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 상품 관리 시스템의 비즈니스 로직을 구현하는 서비스 클래스입니다.
 * 상품에 대한 등록, 조회, 수정, 삭제 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final AdminRepository adminRepository;

    /**
     * 새로운 상품을 등록합니다.
     * @param dto 상품 등록을 위한 요청 데이터
     * @param id 로그인 되어있는 관리자의 고유 ID
     * @return 저장된 상품 정보 (DTO)
     */
    @Transactional
    public ProductResponseDto saveProduct(@Valid ProductRequestDto dto, Long id) {

        Admin admin = adminRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 관리자입니다."));

        return new ProductResponseDto(productRepository.save(dto.toEntity(admin)));
    }

}
