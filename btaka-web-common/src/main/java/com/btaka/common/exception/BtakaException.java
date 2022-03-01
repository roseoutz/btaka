package com.btaka.common.exception;

import com.btaka.board.common.constants.CommonErrorCode;
import com.btaka.board.common.constants.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class BtakaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected HttpStatus httpStatus;

    protected ErrorCode errorCode;

    protected String errorDetail = null;

    protected transient Map<String, Object> dataMap = null;

    public BtakaException(Throwable throwable) {
        super(throwable);

        if (throwable instanceof BtakaException) {
            BtakaException e = (BtakaException) throwable;
            this.httpStatus = e.httpStatus;
            this.errorCode = e.errorCode;
            this.dataMap = e.dataMap;
            this.errorDetail = e.errorDetail;
        } else {
            this.errorCode = CommonErrorCode.COMMON_ERROR_CODE;
            this.errorDetail = throwable.getMessage();
        }
    }

    public BtakaException(HttpStatus httpStatus, Throwable throwable) {
        this(throwable);
        this.httpStatus = httpStatus;
    }

    public BtakaException(HttpStatus httpStatus, ErrorCode errorCode) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }

    public BtakaException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public Map<String, Object> getDataMap() {
        return this.dataMap;
    }

    public String getMessage() {
        return errorCode.getMsgCode();
    }

    public String getDetail() {
        return this.errorDetail;
    }

    public int getStatusCode() {
        return this.httpStatus == null ? 500 : this.httpStatus.value();
    }

}
