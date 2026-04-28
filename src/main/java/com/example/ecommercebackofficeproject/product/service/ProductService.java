package com.example.ecommercebackofficeproject.product.service;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import com.example.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.example.ecommercebackofficeproject.product.dto.request.ProductRequestDto;
import com.example.ecommercebackofficeproject.product.dto.response.CreateProductResponseDto;
import com.example.ecommercebackofficeproject.product.dto.response.GetProductPageResponseDto;
import com.example.ecommercebackofficeproject.product.dto.response.GetProductResponseDto;
import com.example.ecommercebackofficeproject.product.entity.Product;
import com.example.ecommercebackofficeproject.product.repository.ProductRepository;
import com.example.ecommercebackofficeproject.product.type.ProductCategory;
import com.example.ecommercebackofficeproject.product.type.ProductStatus;
import com.example.ecommercebackofficeproject.review.dto.response.RecentReviewResponseDto;
import com.example.ecommercebackofficeproject.review.dto.response.ReviewStatsResponseDto;
import com.example.ecommercebackofficeproject.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 상품 관리 시스템의 비즈니스 로직을 구현하는 서비스 클래스입니다.
 * 상품에 대한 등록, 조회, 수정, 삭제 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ReviewService reviewService;

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
    public Page<GetProductPageResponseDto> getProductList(String keyword, ProductCategory category, ProductStatus status, Pageable pageable) {
        return productRepository.findAllWithFilters(keyword, category, status, pageable).map(GetProductPageResponseDto::new);
    }

    /**
     * 특정 상품의 상세 정보를 조회합니다.
     * 상품의 기본 정보와 더불어, ReviewService를 통해
     * 해당 상품의 리뷰 통계 및 최신 리뷰 목록을 포함하여 반환합니다.
     *
     * @param productId 조회할 상품의 고유 식별자(ID)
     * @return 상품 상세 정보, 리뷰 통계, 최신 리뷰 3건을 포함한 응답 DTO
     * @throws IllegalArgumentException 존재하지 않는 상품 ID일 경우 발생
     * @throws IllegalStateException    이미 삭제된 상품일 경우 발생
     */
    @Transactional(readOnly = true)
    public GetProductResponseDto getProduct(Long productId) {
        // 1. 특정 상품 기본 정보 조회 및 검증
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        if(product.isDeleted()) {
            throw new IllegalStateException("삭제된 상품입니다.");
        }

        // 2. 리뷰 통계 조회
        ReviewStatsResponseDto reviewStats = reviewService.getReviewStat(productId);

        // 3. 최신 리뷰 3건 조회
        List<RecentReviewResponseDto> recentReviews = reviewService.getRecentReviews(productId);

        // 4. 모든 정보를 DTO에 합쳐 반환
        return new GetProductResponseDto(product, reviewStats, recentReviews);
    }

    /**
     * 기존 상품의 기본 정보(상품명, 카테고리, 가격)를 수정합니다.
     * @param productId 수정할 상품의 고유 식별자(ID)
     * @param dto       수정할 데이터(상품명, 카테고리, 가격)를 담은 객체
     * @return 수정된 상품 정보 응답 DTO
     * @throws IllegalArgumentException 존재하지 않는 상품 ID인 경우 발생
     */
    @Transactional
    public GetProductResponseDto updateProductInfo(Long productId, @Valid ProductRequestDto dto) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("발견된 상품이 없습니다."));

        return new GetProductResponseDto(product.updateInfo(dto.getProductName(), dto.getCategory(), dto.getPrice()));
    }

    /**
     * 상품의 판매 상태를 수동으로 변경합니다.
     * @param productId 상태를 변경할 상품의 고유 식별자(ID)
     * @param dto       변경할 상태 정보를 담은 객체
     * @return 상태가 변경된 상품 정보 응답 DTO
     * @throws IllegalArgumentException 존재하지 않는 상품 ID인 경우 발생
     */
    @Transactional
    public GetProductResponseDto updateProductStatus(Long productId, @Valid ProductRequestDto dto) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("발견된 상품이 없습니다."));

        return new GetProductResponseDto(product.updateStatus(dto.getStatus()));
    }

    /**
     * 특정 상품을 논리적으로 삭제합니다.
     * 상품 엔티티의 삭제 상태를 업데이트하여 실제 데이터는 보존하되,
     * 향후 조회 목록에서는 제외되도록 처리합니다.
     * @param productId 삭제할 상품의 고유 식별자(ID)
     * @throws IllegalArgumentException 해당 ID를 가진 상품이 DB에 존재하지 않을 경우 발생
     */
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("발견된 상품이 없습니다."));

        product.delete();
    }
}
