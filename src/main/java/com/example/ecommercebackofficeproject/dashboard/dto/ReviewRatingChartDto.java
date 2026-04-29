package com.example.ecommercebackofficeproject.dashboard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewRatingChartDto {
    private final Integer rating;
    private final Long count;

    @Builder
    public ReviewRatingChartDto(Integer rating, Long count) {
        this.rating = rating;
        this.count = count;
    }
}
