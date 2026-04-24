package com.example.ecommercebackofficeproject.customer.dto.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class GetCustomerPageResponse {
    private List<GetCustomerResponse> items;
    private int page;
    private int size;
    private long totalCount;
    private int totalPages;

    public GetCustomerPageResponse(
            List<GetCustomerResponse> items,
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

    public static GetCustomerPageResponse from(Page<GetCustomerResponse> customerPage) {
        return new GetCustomerPageResponse(
                customerPage.getContent(),
                customerPage.getNumber() + 1,
                customerPage.getSize(),
                customerPage.getTotalElements(),
                customerPage.getTotalPages()
        );
    }

}
