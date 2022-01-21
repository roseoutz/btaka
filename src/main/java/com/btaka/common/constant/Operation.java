package com.btaka.common.constant;

public enum Operation {
    EQUAL("eq"),
    NOT_EQUAL("neq");

    Operation(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

}
