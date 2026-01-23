package com.project.demo.utility.jwt;

public enum TokenEnum {
    ACCESS("access"),REFRESH("refresh"),TOKEN_PREFIX("Bearer ");

    String value;

    TokenEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
