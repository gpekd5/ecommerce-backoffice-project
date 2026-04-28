package com.example.ecommercebackofficeproject.global.exception;

import org.springframework.http.HttpStatus;

/**
 * 리소스 조회 실패 예외 클래스.
 *
 * 요청한 관리자, 상품, 주문 등의 데이터를 찾을 수 없는 경우 사용.
 *
 * HTTP 상태 코드 404 Not Found 반환.
 */
public class NotFoundException extends ServiceException {

    public NotFoundException(String message) {

        super(HttpStatus.NOT_FOUND, message);
    }
}
