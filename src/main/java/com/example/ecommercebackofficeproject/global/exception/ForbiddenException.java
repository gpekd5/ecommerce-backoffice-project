package com.example.ecommercebackofficeproject.global.exception;

import org.springframework.http.HttpStatus;

/**
 * 권한 부족 예외 클래스.
 *
 * 인증은 되었지만 해당 API를 실행할 권한이 없는 경우 사용.
 *
 * HTTP 상태 코드 403 Forbidden 반환.
 */
public class ForbiddenException extends ServiceException {

    public ForbiddenException(String message) {

        super(HttpStatus.FORBIDDEN, message);
    }
}
