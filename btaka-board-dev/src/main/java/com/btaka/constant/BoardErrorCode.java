package com.btaka.constant;

import com.btaka.board.common.constants.ErrorCode;

import java.util.Arrays;

public enum BoardErrorCode implements ErrorCode {
    POST_NOT_FOUND("post.not.found"),
    NOT_AUTHORIZED("not.authorized")
    ;

    private BoardErrorCode(String msgCode) {
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
