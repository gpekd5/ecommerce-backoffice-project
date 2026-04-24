package com.example.ecommercebackofficeproject.product.service;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import com.example.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.example.ecommercebackofficeproject.product.dto.request.ProductRequestDto;
import com.example.ecommercebackofficeproject.product.dto.response.CreateProductResponseDto;
import com.example.ecommercebackofficeproject.product.dto.response.GetProductPageResponseDto;
import com.example.ecommercebackofficeproject.product.dto.response.GetProductResponseDto;
import com.example.ecommercebackofficeproject.product.repository.ProductRepository;
import com.example.ecommercebackofficeproject.product.type.ProductCategory;
import com.example.ecommercebackofficeproject.product.type.ProductStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public CreateProductResponseDto saveProduct(@Valid ProductRequestDto dto, Long id) {

        Admin admin = adminRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 관리자입니다."));

        return new CreateProductResponseDto(productRepository.save(dto.toEntity(admin)));
    }


    /**
     * 상품 전체 조회 및 검색
     * @param keyword  검색어
     * @param category 카테고리 필터
     * @param status   상태 필터 (판매중, 품절 등)
     * @param pageable 페이징 및 정렬 정보 (Spring이 자동 생성)
     */
    @Transactional(readOnly = true)
    public Page<GetProductPageResponseDto> getProductList(String keyword, String category, String status, Pageable pageable) {

        return productRepository.findAllWithFilters(keyword, ProductCategory.valueOf(category), ProductStatus.valueOf(status), pageable).map(GetProductPageResponseDto::new);
    }

    /**
     * 특정 상품의 상세 정보를 조회합니다.
     * @param productId 조회할 상품의 고유 식별자(ID)
     * @return 조회된 상품 상세 정보 DTO
     * @throws IllegalArgumentException 존재하지 않는 상품 ID를 조회하려 할 경우 발생
     */
    @Transactional(readOnly = true)
    public GetProductResponseDto getProduct(Long productId) {
        return productRepository.findById(productId).map(GetProductResponseDto::new).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }
}
