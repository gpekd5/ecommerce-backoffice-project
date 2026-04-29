package com.example.ecommercebackofficeproject.review.dto.response;

import com.example.ecommercebackofficeproject.product.dto.response.ProductResponseDto;
import com.example.ecommercebackofficeproject.review.entity.Review;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 리뷰 전체 조회 시 응답 데이터로 사용되는 DTO 클래스입니다.
 * Product 엔티티를 클라이언트에 필요한 정보만 담은 응답 객체로 변환합니다.
 */
@Getter
public class GetReviewPageResponseDto {

    List<ReviewResponseDto> content;
    private final Integer currentPage;
    private final Integer size;
    private final Long totalElements;
    private final Integer totalPages;

    /**
     * Review 엔티티 객체를 바탕으로 응답용 DTO를 생성합니다.
     */
    public GetReviewPageResponseDto(Page<Review> page) {
        this.content = page.getContent().stream().map(ReviewResponseDto::new).toList();
        this.currentPage = page.getNumber() + 1;
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }
}
