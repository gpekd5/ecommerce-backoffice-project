package com.example.ecommercebackofficeproject.review.dto.response;

import com.example.ecommercebackofficeproject.product.dto.response.ProductResponseDto;
import com.example.ecommercebackofficeproject.review.entity.Review;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 리뷰 목록 조회 시 페이징 정보와 데이터를 함께 반환하는 응답 DTO 클래스입니다.
 */
@Getter
public class GetReviewPageResponseDto {

    List<ReviewResponseDto> content;
    private final Integer currentPage;
    private final Integer size;
    private final Long totalElements;
    private final Integer totalPages;

    /**
     * Spring Data JPA의 Page 객체를 바탕으로 응답용 DTO를 생성합니다.
     * 사용자 편의를 위해 0부터 시작하는 페이지 번호를 1부터 시작하도록 보정합니다.
     *
     * @param page 리뷰 엔티티 페이지 객체
     */
    public GetReviewPageResponseDto(Page<Review> page) {
        this.content = page.getContent().stream().map(ReviewResponseDto::new).toList();
        this.currentPage = page.getNumber() + 1;
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }
}
