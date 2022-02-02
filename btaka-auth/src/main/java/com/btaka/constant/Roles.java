package com.btaka.constant;

import lombok.Getter;

@Getter
public enum Roles {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER")
    ;

    private String role;

    Roles(String role) {
        this.role = role;
    }
}
