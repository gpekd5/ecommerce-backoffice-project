package com.example.ecommercebackofficeproject.customer.dto.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 고객 목록 조회의 페이징 응답 데이터를 담는 DTO.
 *
 * 고객 목록과 함께 현재 페이지, 페이지 크기,
 * 전체 고객 수, 전체 페이지 수를 응답할 때 사용한다.
 */
@Getter
public class GetCustomerPageResponseDto {

    /**
     * 조회된 고객 목록.
     */
    private List<GetCustomerResponseDto> items;

    /**
     * 현재 페이지 번호.
     *
     * 클라이언트 기준의 1-based 페이지 번호이다.
     */
    private int page;

    /**
     * 한 페이지에 조회된 데이터 수.
     */
    private int size;

    /**
     * 조회 조건에 해당하는 전체 고객 수.
     */
    private long totalCount;

    /**
     * 전체 페이지 수.
     */
    private int totalPages;

    /**
     * 고객 목록 페이징 응답 DTO를 생성한다.
     *
     * @param items 조회된 고객 목록
     * @param page 현재 페이지 번호
     * @param size 페이지 크기
     * @param totalCount 전체 고객 수
     * @param totalPages 전체 페이지 수
     */
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

    /**
     * Page 객체와 고객 목록 데이터를 고객 목록 페이징 응답 DTO로 변환한다.
     *
     * Page 객체에서 현재 페이지, 페이지 크기,
     * 전체 데이터 수, 전체 페이지 수를 추출한다.
     *
     * @param pageData 페이징 정보를 가진 Page 객체
     * @param items 조회된 고객 목록
     * @return 고객 목록 페이징 응답 DTO
     */
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