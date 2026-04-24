package com.example.ecommercebackofficeproject.admin.type;

public enum AdminStatus {
    PENDING("승인대기"),
    ACTIVE("활성"),
    INACTIVE("비활성"),
    SUSPENDED("정지"),
    REJECTED("거부");

    private final String description;

    AdminStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
