package com.example.ecommercebackofficeproject.admin.type;

/**
 * 관리자 역할 Enum.
 *
 * 관리자 권한 구분용 역할 정보 관리.
 */
public enum AdminRole {

    /**
     * 슈퍼 관리자.
     *
     * 전체 관리자 조회 및 관리 권한 보유.
     */
    SUPER("슈퍼 관리자"),

    /**
     * 운영 관리자.
     *
     * 운영 관련 기능 관리 권한 보유.
     */
    OPERATION("운영 관리자"),

    /**
     * CS 관리자.
     *
     * 고객 응대 관련 기능 관리 권한 보유.
     */
    CS("CS 관리자");

    /**
     * 관리자 역할 설명.
     */
    private final String description;

    /**
     * 관리자 역할 Enum 생성자.
     *
     * @param description 관리자 역할 설명
     */
    AdminRole(String description) {
        this.description = description;
    }

    /**
     * 관리자 역할 설명 조회.
     *
     * @return 관리자 역할 설명
     */
    public String getDescription() {
        return description;
    }
}