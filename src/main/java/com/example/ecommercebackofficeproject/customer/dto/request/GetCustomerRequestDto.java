package com.example.ecommercebackofficeproject.customer.dto.request;

import lombok.Getter;

/**
 * 고객 목록 조회 요청 조건을 담는 DTO.
 *
 * 검색 키워드, 고객 상태, 페이지 번호, 페이지 크기,
 * 정렬 기준, 정렬 방향을 요청 파라미터로 전달받는다.
 */
@Getter
public class GetCustomerRequestDto {

    /**
     * 고객 검색 키워드.
     *
     * 고객 이름, 이메일 등 검색 조건으로 사용된다.
     */
    private String keyword;

    /**
     * 고객 상태 필터 조건.
     *
     * 예: ACTIVE, INACTIVE, SUSPENDED
     */
    private String status;

    /**
     * 조회할 페이지 번호.
     *
     * 기본값은 1이며, 클라이언트 기준의 1-based 페이지 번호로 사용된다.
     */
    private int page = 1;

    /**
     * 한 페이지에 조회할 고객 수.
     *
     * 기본값은 10이다.
     */
    private int size = 10;

    /**
     * 정렬 기준 필드명.
     *
     * 기본값은 createdAt이다.
     */
    private String sortBy = "createdAt";

    /**
     * 정렬 방향.
     *
     * 기본값은 desc이며, asc 또는 desc 값을 사용한다.
     */
    private String sortDir = "desc";

}