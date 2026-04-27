package com.example.ecommercebackofficeproject.admin.dto.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 관리자 목록 페이지 응답 DTO.
 *
 * 관리자 목록 데이터와 페이지네이션 정보 전달.
 */
@Getter
public class GetAdminsPageResponseDto {

    /**
     * 관리자 목록 데이터.
     */
    private final List<GetAdminResponseDto> content;

    /**
     * 현재 페이지 번호.
     *
     * 클라이언트 기준 1번부터 시작.
     */
    private final int currentPage;

    /**
     * 페이지당 조회 개수.
     */
    private final int size;

    /**
     * 전체 관리자 수.
     */
    private final Long totalElements;

    /**
     * 전체 페이지 수.
     */
    private final int totalPage;

    /**
     * 관리자 목록 페이지 응답 DTO 생성자.
     *
     * Page 객체의 관리자 목록 데이터와 페이지네이션 정보 변환.
     *
     * @param page 관리자 목록 페이지 객체
     */
    public GetAdminsPageResponseDto(Page<GetAdminResponseDto> page) {
        this.content = page.getContent();
        this.currentPage = page.getNumber() + 1;
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPage = page.getTotalPages();
    }
}
