package com.btaka.config.constant;

import com.btaka.board.common.constants.ErrorCode;

import java.util.Arrays;

public enum ConfigErrorCode implements ErrorCode {
    CONFIG_KEY_NOT_FOUND("error.config.key.not.found"),
    CONFIG_GROUP_NOT_FOUND("error.config.group.not.found")
    ;
    private ConfigErrorCode(String msgCode) {
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
