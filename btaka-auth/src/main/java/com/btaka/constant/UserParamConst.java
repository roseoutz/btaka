package com.btaka.constant;

public enum UserParamConst {
    PARAM_USER_OBJECT_ID("oid"),
    PARAM_USER_ID("userId"),
    PARAM_USER_EMAIL("email"),
    PARAM_USER_NAME("username"),
    PARAM_USER_IS_EXIST("isExist"),
    PARAM_USER_INFO("userinfo")
    ;

    private final String key;

    UserParamConst(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
