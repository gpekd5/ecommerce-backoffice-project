package com.example.ecommercebackofficeproject.global.exception;

import org.springframework.http.HttpStatus;

/**
 * 잘못된 요청 예외 클래스.
 *
 * 클라이언트의 요청 값이 올바르지 않거나,
 * 요청 조건을 만족하지 못한 경우 사용.
 *
 * HTTP 상태 코드 400 Bad Request 반환.
 */
public class BadRequestException extends ServiceException {

    public BadRequestException(String message) {

        super(HttpStatus.BAD_REQUEST, message);
    }
}
