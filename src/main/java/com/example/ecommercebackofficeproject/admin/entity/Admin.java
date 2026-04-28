package com.example.ecommercebackofficeproject.admin.entity;

import com.example.ecommercebackofficeproject.admin.type.AdminRole;
import com.example.ecommercebackofficeproject.admin.type.AdminStatus;
import com.example.ecommercebackofficeproject.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 관리자 엔티티.
 *
 * 관리자 계정 정보, 역할, 상태, 승인 및 거절 정보 관리.
 */
@Getter
@Entity
@Table(name = "admins")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity {

    /**
     * 관리자 ID.
     *
     * 기본키.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 관리자 이메일.
     *
     * 로그인 계정 식별값.
     * 중복 불가 및 최대 100자 제한.
     */
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /**
     * 관리자 비밀번호.
     *
     * 암호화된 비밀번호 저장.
     * 최대 255자 제한.
     */
    @Column(nullable = false, length = 255)
    private String password;

    /**
     * 관리자 이름.
     *
     * 최대 30자 제한.
     */
    @Column(nullable = false, length = 30)
    private String name;

    /**
     * 관리자 전화번호.
     *
     * 최대 20자 제한.
     */
    @Column(length = 20)
    private String phone;

    /**
     * 관리자 역할.
     *
     * 관리자 권한 구분용 Enum 값.
     */
    @Enumerated(EnumType.STRING)
    private AdminRole role;

    /**
     * 관리자 상태.
     *
     * 승인 대기, 활성, 비활성, 정지, 거절 등의 상태 구분용 Enum 값.
     */
    @Enumerated(EnumType.STRING)
    private AdminStatus status;

    /**
     * 관리자 승인일시.
     *
     * 승인 처리 시각 저장.
     */
    @Column
    private LocalDateTime approvedAt;

    /**
     * 관리자 거절일시.
     *
     * 승인 거절 처리 시각 저장.
     */
    @Column
    private LocalDateTime rejectedAt;

    /**
     * 관리자 승인 거절 사유.
     *
     * 최대 100자 제한.
     */
    @Column(length = 100)
    private String rejectReason;

    /**
     * 관리자 엔티티 생성자.
     *
     * 관리자 회원가입 시 필요한 기본 정보 초기화.
     *
     * @param email 관리자 이메일
     * @param password 관리자 비밀번호
     * @param name 관리자 이름
     * @param phone 관리자 전화번호
     * @param role 관리자 역할
     * @param status 관리자 상태
     */
    public Admin(
            String email, String password, String name,
            String phone, AdminRole role, AdminStatus status
    ) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.status = status;
    }

    public void updateInfo(String name, String email, String phone) {
        if (name != null) {
            this.name = name;
        }

        if (email != null) {
            this.email = email;
        }

        if (phone != null) {
            this.phone = phone;
        }
    }

    public void updateRole(AdminRole role) {
        this.role = role;
    }

    public void updateStatus(AdminStatus status) {
        this.status = status;
    }

    public void approve() {
        this.status = AdminStatus.ACTIVE;
        this.approvedAt = LocalDateTime.now();
    }

    public void reject(String rejectReason) {
        this.status = AdminStatus.REJECTED;
        this.rejectReason = rejectReason;
        this.rejectedAt = LocalDateTime.now();
    }

    public void updatePassword(String password) {
        this.password = password;
    }

}