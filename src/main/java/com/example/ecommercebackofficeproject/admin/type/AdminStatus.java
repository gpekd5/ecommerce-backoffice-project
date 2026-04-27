package com.example.ecommercebackofficeproject.admin.type;

/**
 * 관리자 상태 Enum.
 *
 * 관리자 계정의 승인 및 사용 상태 구분용 상태 정보 관리.
 */
public enum AdminStatus {

    /**
     * 승인 대기 상태.
     *
     * 회원가입 후 슈퍼 관리자 승인 전 상태.
     */
    PENDING("승인대기"),

    /**
     * 활성 상태.
     *
     * 로그인 및 관리자 기능 사용 가능 상태.
     */
    ACTIVE("활성"),

    /**
     * 비활성 상태.
     *
     * 계정 사용이 일시적으로 비활성화된 상태.
     */
    INACTIVE("비활성"),

    /**
     * 정지 상태.
     *
     * 관리자에 의해 계정 사용이 제한된 상태.
     */
    SUSPENDED("정지"),

    /**
     * 거부 상태.
     *
     * 관리자 가입 승인 요청이 거부된 상태.
     */
    REJECTED("거부");

    /**
     * 관리자 상태 설명.
     */
    private final String description;

    /**
     * 관리자 상태 Enum 생성자.
     *
     * @param description 관리자 상태 설명
     */
    AdminStatus(String description) {
        this.description = description;
    }

    /**
     * 관리자 상태 설명 조회.
     *
     * @return 관리자 상태 설명
     */
    public String getDescription() {
        return description;
    }
}