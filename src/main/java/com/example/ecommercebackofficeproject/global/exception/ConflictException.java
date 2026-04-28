package com.example.ecommercebackofficeproject.global.exception;

import org.springframework.http.HttpStatus;

/**
 * 데이터 충돌 예외 클래스.
 *
 * 요청 값은 유효하지만 현재 서버의 데이터 상태와 충돌하여
 * 요청을 처리할 수 없는 경우 사용.
 *
 * 예: 중복 이메일, 중복 상품명, 이미 처리된 승인 요청 등.
 *
 * HTTP 상태 코드 409 Conflict 반환.
 */
public class ConflictException extends ServiceException {

    public ConflictException(String message) {

        super(HttpStatus.CONFLICT, message);
    }
}
