package com.example.ecommercebackofficeproject.global.exception;

import org.springframework.http.HttpStatus;

/**
 * 인증 실패 예외 클래스.
 *
 * 로그인하지 않은 사용자가 인증이 필요한 API에 접근하거나,
 * 인증 정보가 유효하지 않은 경우 사용.
 *
 * HTTP 상태 코드 401 Unauthorized 반환.
 */
public class UnauthorizedException extends ServiceException {

    public UnauthorizedException(String message) {

        super(HttpStatus.UNAUTHORIZED, message);
    }
}
