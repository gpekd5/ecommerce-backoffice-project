package com.example.ecommercebackofficeproject.admin.dto.request;

import com.example.ecommercebackofficeproject.admin.type.AdminRole;
import com.example.ecommercebackofficeproject.admin.type.AdminStatus;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class UpdateAdminRequestDto {

    /**
     * 관리자 이름.
     *
     * 최대 30자 제한.
     */
    @Size(max = 30, message = "이름은 30자 이하로 입력해주세요.")
    private final String name;

    /**
     * 관리자 이메일.
     *
     * 이메일 형식 검증 및 최대 100자 제한.
     */
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @Size(max = 100, message = "이메일은 100자 이하로 입력해주세요.")
    private final String email;

    /**
     * 관리자 전화번호.
     *
     * 010-0000-0000 형식 검증.
     */
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식은 010-0000-0000 형식이어야 합니다.")
    private final String phone;

    public UpdateAdminRequestDto(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
