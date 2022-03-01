package com.btaka.board.common.constants;

import java.util.Arrays;
import java.util.stream.Stream;

public enum CommonErrorCode implements ErrorCode {

    COMMON_ERROR_CODE("common.error")

    ;
    private CommonErrorCode(String msgCode) {
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
        return Arrays.stream(values()).filter(value -> value.isEqual(code)).findFirst().orElse(COMMON_ERROR_CODE);
    }
}
