package com.example.ecommercebackofficeproject.global.exception;

import com.example.ecommercebackofficeproject.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 애플리케이션 전역에서 발생하는 예외를 핸들링하는 클래스입니다.
 * 각 예외 상황에 맞는 HTTP 상태 코드와 메시지를 클라이언트에게 반환합니다.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 잘못된 인자 값이 전달되었을 때 발생하는 예외를 처리합니다.
     *
     * @param e IllegalArgumentException 객체
     * @return 400 Bad Request와 에러 메시지
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.fail(
                        HttpStatus.BAD_REQUEST,
                        e.getMessage()
                ));
    }

    /**
     * 서비스 계층 커스텀 예외 처리.
     * <p>
     * ServiceException을 상속한 BadRequestException, ForbiddenException,
     * NotFoundException, UnauthorizedException, ConflictException 등을 처리.
     *
     * @param e ServiceException 객체
     * @return 예외에 설정된 HTTP 상태 코드와 에러 메시지
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiResponse<Void>> handleServiceException(ServiceException e) {
        log.error("ServiceException: {}", e.getMessage()); //개발자 확인용

        return ResponseEntity.status(e.getStatus()).body(
                ApiResponse.fail(
                        e.getStatus(),
                        e.getMessage()
                ));
    }

    /**
     * 요청 값 검증 예외 처리.
     *
     * @param e MethodArgumentNotValidException 객체
     * @return 400 Bad Request와 검증 실패 메시지
     * @Valid 검증 실패 시 발생하는 MethodArgumentNotValidException 처리.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("입력 값이 올바르지 않습니다.");
        log.error("ValidationException: {}", errorMessage); //개발자 확인용
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.fail(
                        HttpStatus.BAD_REQUEST,
                        errorMessage
                ));
    }

}
