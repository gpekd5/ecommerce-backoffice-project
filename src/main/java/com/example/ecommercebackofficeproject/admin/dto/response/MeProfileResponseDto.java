package com.example.ecommercebackofficeproject.admin.dto.response;

import com.example.ecommercebackofficeproject.admin.entity.Admin;
import lombok.Getter;

@Getter
public class MeProfileResponseDto {

    /**
     * 프로필 ID.
     */
    private final Long id;

    /**
     * 프로필 이메일.
     */
    private final String email;

    /**
     * 프로필 이름.
     */
    private final String name;

    /**
     * 프로필 전화번호.
     */
    private final String phone;

    /**
     * 프로필 조회 응답 DTO 생성자.
     *
     * @param id 관리자 ID
     * @param email 관리자 이메일
     * @param name 관리자 이름
     * @param phone 관리자 전화번호
     */
    public MeProfileResponseDto(Long id, String email, String name, String phone) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }

    /**
     * Admin 엔티티를 프로필 조회 응답 DTO로 변환.
     *
     * @param admin 관리자 엔티티
     * @return 프로필 조회 응답 DTO
     */
    public static MeProfileResponseDto from(Admin admin) {
        return new MeProfileResponseDto(
                admin.getId(),
                admin.getEmail(),
                admin.getName(),
                admin.getPhone()
        );
    }}
