package com.example.ecommercebackofficeproject.review.dto.response;

import lombok.Getter;

import java.util.Map;

/**
 * 상품의 리뷰 통계 정보를 전달하기 위한 응답 DTO입니다.
 * 평균 평점, 총 리뷰 개수, 그리고 각 별점(1~5점)별 리뷰 분포 정보를 포함합니다.
 */
@Getter
public class ReviewStatsResponseDto {

    private final double averageRating;                       // 평균평점
    private final long totalCount;                            // 총 리뷰 개수
    private final Map<Integer, Long> ratingDistribution;       // 별점별 개수

    /**
     * 리뷰 통계 DTO를 생성합니다.
     * @param averageRating      DB에서 계산된 평균 평점
     * @param totalCount         전체 리뷰 개수
     * @param ratingDistribution 별점별 카운트가 담긴 맵
     */
    public ReviewStatsResponseDto(double averageRating, long totalCount, Map<Integer, Long> ratingDistribution) {
        this.averageRating = averageRating;
        this.totalCount = totalCount;
        this.ratingDistribution = ratingDistribution;
    }
}
