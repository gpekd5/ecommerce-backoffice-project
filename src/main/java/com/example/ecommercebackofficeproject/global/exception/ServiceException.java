package com.example.ecommercebackofficeproject.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 서비스 계층 공통 예외 클래스.
 *
 * 애플리케이션에서 직접 정의한 커스텀 예외들의 부모 클래스.
 * 각 예외는 HTTP 상태 코드와 예외 메시지를 함께 가짐.
 */
@Getter
public class ServiceException extends RuntimeException {

    private final HttpStatus status;

    public ServiceException(HttpStatus status,String message) {

        super(message);
        this.status = status;

    }
}
