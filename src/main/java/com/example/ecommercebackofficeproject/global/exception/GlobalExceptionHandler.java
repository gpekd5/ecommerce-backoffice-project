package com.example.ecommercebackofficeproject.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 애플리케이션 전역에서 발생하는 예외를 핸들링하는 클래스입니다.
 * 각 예외 상황에 맞는 HTTP 상태 코드와 메시지를 클라이언트에게 반환합니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 잘못된 인자 값이 전달되었을 때 발생하는 예외를 처리합니다.
     * @param e IllegalArgumentException 객체
     * @return 400 Bad Request와 에러 메시지
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
