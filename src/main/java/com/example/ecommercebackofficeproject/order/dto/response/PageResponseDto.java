package com.example.ecommercebackofficeproject.order.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PageResponseDto {
    private final Integer currentPage;
    private final Integer size;
    private final Long totalElements;
    private final Integer totalPages;

    @Builder
    public PageResponseDto(Integer currentPage, Integer size, Long totalElements, Integer totalPages) {
        this.currentPage = currentPage;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
