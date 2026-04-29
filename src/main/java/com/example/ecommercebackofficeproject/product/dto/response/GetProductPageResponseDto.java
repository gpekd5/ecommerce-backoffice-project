package com.example.ecommercebackofficeproject.product.dto.response;

import com.example.ecommercebackofficeproject.product.entity.Product;
import com.example.ecommercebackofficeproject.product.type.ProductCategory;
import com.example.ecommercebackofficeproject.product.type.ProductStatus;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 상품 목록 조회 시 페이징 정보를 포함하여 반환하는 응답 DTO 클래스입니다.
 */
@Getter
public class GetProductPageResponseDto {

    private final List<ProductResponseDto> content;
    private final Integer currentPage;
    private final Integer size;
    private final Long totalElements;
    private final Integer totalPages;

    /**
     * Spring Data JPA의 Page 객체를 응답용 DTO로 변환합니다.
     * @param page 상품 엔티티 페이징 객체
     */
    public GetProductPageResponseDto(Page<Product> page) {
        this.content = page.getContent().stream().map(ProductResponseDto::new).toList();
        this.currentPage = page.getNumber() + 1;
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }
}
