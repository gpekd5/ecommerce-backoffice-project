package com.example.ecommercebackofficeproject.admin.type;

public enum AdminRole {
    SUPER("슈퍼 관리자"),
    OPERATION("운영 관리자"),
    CS("CS 관리자");

    private final String description;

    AdminRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}


