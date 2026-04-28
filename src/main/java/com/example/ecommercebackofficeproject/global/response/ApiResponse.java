package com.example.ecommercebackofficeproject.global.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * API 공통 응답 DTO.
 *
 * 모든 API의 성공/실패 응답을 동일한 형식으로 반환하기 위한 객체.
 *
 * @param <T> 응답 데이터 타입
 */
@Getter
public class ApiResponse<T> {

    private final int status;
    private final String message;
    private final T data;

    /**
     * API 공통 응답 DTO 생성자.
     *
     * @param status HTTP 상태 코드
     * @param message 응답 메시지
     * @param data 응답 데이터
     */
    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * 성공 응답 생성.
     *
     * @param status HTTP 상태
     * @param message 성공 메시지
     * @param data 응답 데이터
     * @return 성공 공통 응답 DTO
     */
    public static <T> ApiResponse<T> success(HttpStatus status, String message, T data) {
        return new ApiResponse<>(status.value(), message, data);
    }

    /**
     * 실패 응답 생성.
     *
     * @param status HTTP 상태
     * @param message 실패 메시지
     * @return 실패 공통 응답 DTO
     */
    public static <T> ApiResponse<T> fail(HttpStatus status, String message) {
        return new ApiResponse<>(status.value(), message, null);
    }
}
