package com.example.ecommercebackofficeproject.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 관리자 로그인 요청 DTO.
 *
 * 관리자 로그인 시 필요한 이메일과 비밀번호 정보 전달.
 * 각 필드에 대한 유효성 검증 조건 포함.
 */
@Getter
public class LoginRequestDto {

    /**
     * 관리자 이메일.
     *
     * 필수 입력값.
     * 이메일 형식 검증.
     */
    @NotBlank(message = "이메일은 필수 입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private final String email;

    /**
     * 관리자 비밀번호.
     *
     * 필수 입력값.
     */
    @NotBlank(message = "비밀번호는 필수입니다.")
    private final String password;

    /**
     * 관리자 로그인 요청 DTO 생성자.
     *
     * @param email 관리자 이메일
     * @param password 관리자 비밀번호
     */
    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}