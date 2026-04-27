package com.example.ecommercebackofficeproject.admin.dto.request;

import com.example.ecommercebackofficeproject.admin.type.AdminRole;
import jakarta.validation.constraints.*;
import lombok.Getter;

/**
 * 관리자 회원가입 요청 DTO.
 *
 * 관리자 회원가입 시 필요한 이메일, 비밀번호, 이름, 전화번호, 역할 정보 전달.
 * 각 필드에 대한 유효성 검증 조건 포함.
 */
@Getter
public class SignupAdminRequestDto {

    /**
     * 관리자 이메일.
     *
     * 필수 입력값.
     * 이메일 형식 검증 및 최대 100자 제한.
     */
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @Size(max = 100, message = "이메일은 100자 이하로 입력해주세요.")
    private final String email;

    /**
     * 관리자 비밀번호.
     *
     * 필수 입력값.
     * 최소 8자, 최대 20자 제한.
     */
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private final String password;

    /**
     * 관리자 이름.
     *
     * 필수 입력값.
     * 최대 30자 제한.
     */
    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 30, message = "이름은 30자 이하로 입력해주세요.")
    private final String name;

    /**
     * 관리자 전화번호.
     *
     * 필수 입력값.
     * 010-0000-0000 형식 검증.
     */
    @NotBlank(message = "전화번호는 필수 입니다.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식은 010-0000-0000 형식이어야 합니다.")
    private final String phone;

    /**
     * 관리자 역할.
     *
     * 필수 입력값.
     * 관리자 권한 구분용 역할 정보.
     */
    @NotNull(message = "관리자 역할은 필수값입니다.")
    private final AdminRole role;

    /**
     * 관리자 회원가입 요청 DTO 생성자.
     *
     * @param email 관리자 이메일
     * @param password 관리자 비밀번호
     * @param name 관리자 이름
     * @param phone 관리자 전화번호
     * @param role 관리자 역할
     */
    public SignupAdminRequestDto(String email, String password, String name, String phone, AdminRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }
}
