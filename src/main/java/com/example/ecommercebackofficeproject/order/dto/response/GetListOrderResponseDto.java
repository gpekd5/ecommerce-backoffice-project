package com.example.ecommercebackofficeproject.order.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class GetListOrderResponseDto {
    private final List<GetListOrderItemResponseDto> itemList;
    private final PageResponseDto pageResponse;

    @Builder
    public GetListOrderResponseDto(List<GetListOrderItemResponseDto> itemList, PageResponseDto pageResponse) {
        this.itemList = itemList;
        this.pageResponse = pageResponse;
    }
}
