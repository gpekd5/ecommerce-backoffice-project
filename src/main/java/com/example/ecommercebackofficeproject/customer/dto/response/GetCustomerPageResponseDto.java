package com.example.ecommercebackofficeproject.customer.dto.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class GetCustomerPageResponseDto {
    private List<GetCustomerResponseDto> items;
    private int page;
    private int size;
    private long totalCount;
    private int totalPages;

    public GetCustomerPageResponseDto(
            List<GetCustomerResponseDto> items,
            int page,
            int size,
            long totalCount,
            int totalPages
    ) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.totalCount = totalCount;
        this.totalPages = totalPages;
    }

    public static GetCustomerPageResponseDto from(
            Page<?> pageData,
            List<GetCustomerResponseDto> items
    ) {
        return new GetCustomerPageResponseDto(
                items,
                pageData.getNumber() + 1,
                pageData.getSize(),
                pageData.getTotalElements(),
                pageData.getTotalPages()
        );
    }

}
