package com.example.ecommercebackofficeproject.admin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdatePasswordRequestDto {

    /**
     * 현재 비밀번호.
     *
     * 필수 입력값.
     * 최소 8자, 최대 20자 제한.
     */
    @NotBlank(message = "현재 비밀번호는 필수입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private final String currentPassword;

    /**
     * 새로운 비밀번호.
     *
     * 필수 입력값.
     * 최소 8자, 최대 20자 제한.
     */
    @NotBlank(message = "새로운 비밀번호는 필수입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private final String newPassword;

    /**
     * 새로운 비밀번호 확인.
     *
     * 필수 입력값.
     * 최소 8자, 최대 20자 제한.
     */
    @NotBlank(message = "새로운 비밀번호 확인은 필수입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private final String newPasswordConfirm ;

    /**
     * 비밀번호 변경 요청 DTO 생성자.
     *
     * @param currentPassword 현재 비밀번호
     * @param newPassword 새로운 비밀번호
     * @param newPasswordConfirm 새로운 비밀번호 확인
     */
    public UpdatePasswordRequestDto(String currentPassword, String newPassword, String newPasswordConfirm) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.newPasswordConfirm = newPasswordConfirm;
    }
}

