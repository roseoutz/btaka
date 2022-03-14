package com.btaka.constant;

import com.btaka.board.common.constants.ErrorCode;

import java.util.Arrays;

public enum AuthErrorCode implements ErrorCode {
    USER_NOT_FOUND("user.not.found"),
    USER_LOCKED("user.locked"),
    PASSWORD_IS_EMPTY("password.is.empty"),
    PASSWORD_NOT_MATCH("password.not.match"),
    PASSWORD_ORIGIN_NOT_MATCH("password.origin.not.match"),
    PASSWORD_CHECK_NOT_MATCH("password.check.not.match"),
    NOT_REGISTER_OAUTH_USER("user.not.register.oauth"),
    ALREADY_REGISTER_USER("already.register.user"),
    NOT_LOGIN("not.login"),
    TOKEN_EXPIRED("token.expired")
    ;

    private AuthErrorCode(String msgCode) {
        this.msgCode = msgCode;
    }

    private String msgCode;

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getMsgCode() {
        return msgCode;
    }

    @Override
    public boolean isEqual(String code) {
        return this.getCode().equalsIgnoreCase(code);
    }

    @Override
    public boolean isEqual(ErrorCode errorCode) {
        return this.isEqual(errorCode.getCode());
    }

    @Override
    public ErrorCode findByCode(String code) {
        return Arrays.stream(values()).filter(value -> value.isEqual(code)).findFirst().orElse(null);
    }
}
