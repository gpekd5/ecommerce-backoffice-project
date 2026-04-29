package com.example.ecommercebackofficeproject.product.service;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import com.example.ecommercebackofficeproject.admin.repository.AdminRepository;
import com.example.ecommercebackofficeproject.global.exception.NotFoundException;
import com.example.ecommercebackofficeproject.product.dto.request.ProductRequestDto;
import com.example.ecommercebackofficeproject.product.dto.request.ProductUpdateInfoDto;
import com.example.ecommercebackofficeproject.product.dto.request.ProductUpdateStatusDto;
import com.example.ecommercebackofficeproject.product.dto.response.CreateProductResponseDto;
import com.example.ecommercebackofficeproject.product.dto.response.GetProductPageResponseDto;
import com.example.ecommercebackofficeproject.product.dto.response.GetProductResponseDto;
import com.example.ecommercebackofficeproject.product.dto.response.ProductResponseDto;
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
 * 상품 관리 시스템의 비즈니스 로직을 담당하는 서비스 클래스입니다.
 * 상품 등록, 페이징 조회, 상세 정보 제공(리뷰 포함), 수정 및 논리 삭제 기능을 처리합니다.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ReviewService reviewService;
    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

    /**
     * 새로운 상품을 시스템에 등록합니다.
     * @param dto 상품 등록 정보 (상품명, 가격, 재고, 카테고리 등)
     * @param id  JWT를 통해 인증된 관리자의 고유 식별자
     * @return    등록 완료된 상품의 상세 데이터
     * @throws NotFoundException 관리자 ID가 유효하지 않을 경우 발생
     */
    @Transactional
    public CreateProductResponseDto saveProduct(@Valid ProductRequestDto dto, Long id) {

        Admin admin = adminRepository.findById(id).orElseThrow(() -> new NotFoundException("admin with ID " + id + "not found."));

        return new CreateProductResponseDto(productRepository.save(dto.toEntity(admin)));
    }

    /**
     * 필터 조건에 따른 상품 목록을 페이징 처리하여 조회합니다.
     * @param keyword  상품명 검색어 (선택 사항)
     * @param category 카테고리 필터 (선택 사항)
     * @param status   상품 판매 상태 필터 (선택 사항)
     * @param pageable 페이징 및 정렬 정보
     * @return         필터링된 상품 목록과 페이징 메타데이터를 포함한 응답 DTO
     */
    @Transactional(readOnly = true)
    public GetProductPageResponseDto getProductList(String keyword, ProductCategory category, ProductStatus status, Pageable pageable) {
        // 1. DB에서 페이징 처리된 엔티티 묶음을 가져옵니다.
        Page<Product> productPage = productRepository.findAllWithFilters(keyword, category, status, pageable);

        // 2. 생성자를 통해 요구하신 5가지 필드(content, page정보들)만 딱 생성해서 반환합니다.
        return new GetProductPageResponseDto(productPage);
    }

    /**
     * 특정 상품의 상세 정보와 관련 리뷰 통계를 함께 조회합니다.
     * @param productId 조회할 상품의 고유 ID
     * @return          상품 정보, 리뷰 평균 평점, 최신 리뷰 3건을 결합한 데이터
     * @throws NotFoundException     해당 ID의 상품이 DB에 존재하지 않을 경우 발생
     * @throws IllegalStateException 이미 삭제(Soft Delete)된 상품을 조회하려 할 경우 발생
     */
    @Transactional(readOnly = true)
    public GetProductResponseDto getProduct(Long productId) {
        // 1. 특정 상품 기본 정보 조회 및 검증
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("product with ID " + productId + "not found."));

        if(product.isDeleted()) {
            throw new NotFoundException("존재하지 않는 상품 입니다.");
        }

        // 2. 리뷰 통계 조회
        ReviewStatsResponseDto reviewStats = reviewService.getReviewStat(productId);

        // 3. 최신 리뷰 3건 조회
        List<RecentReviewResponseDto> recentReviews = reviewService.getRecentReviews(productId);

        // 4. 모든 정보를 DTO에 합쳐 반환
        return new GetProductResponseDto(product, reviewStats, recentReviews);
    }

    /**
     * 기존 상품의 정보(이름, 카테고리, 가격)를 수정합니다.
     * @param productId 수정할 상품의 고유 ID
     * @param dto       변경하고자 하는 상품 정보 데이터
     * @return          수정 완료된 상품의 정보
     * @throws NotFoundException 수정할 상품이 존재하지 않을 경우 발생
     */
    @Transactional
    public ProductResponseDto updateProductInfo(Long productId, @Valid ProductUpdateInfoDto dto) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("product with ID " + productId + "not found."));

        return new ProductResponseDto(product.updateInfo(dto.getProductName(), dto.getCategory(), dto.getPrice()));
    }

    /**
     * 상품의 판매 상태(판매중, 품절 등)를 수동으로 변경합니다.
     * @param productId 상태를 변경할 상품의 고유 ID
     * @param dto       변경할 새로운 상태 값 (컨트롤러에서 유효성이 검증된 상태로 전달됨)
     * @return          상태 변경이 반영된 상품 정보
     * @throws NotFoundException 대상 상품을 찾을 수 없을 경우 발생
     */
    @Transactional
    public ProductResponseDto updateProductStatus(Long productId, @Valid ProductUpdateStatusDto dto) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("product with ID " + productId + "not found."));

        return new ProductResponseDto(product.updateStatus(dto.getStatus()));
    }

    /**
     * 특정 상품을 논리적으로 삭제(Soft Delete)합니다.
     * DB에서 데이터를 제거하지 않고, 삭제 플래그를 통해 노출되지 않도록 처리합니다.
     * @param productId 삭제할 상품의 고유 ID
     * @throws NotFoundException 삭제할 상품이 존재하지 않을 경우 발생
     */
    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("product with ID " + productId + "not found."));

        product.delete();
    }
}
