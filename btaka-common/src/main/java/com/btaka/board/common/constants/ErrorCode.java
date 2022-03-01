package com.btaka.board.common.constants;

import java.io.Serializable;

public interface ErrorCode extends Serializable {

    ErrorCode findByCode(String code);

    String getCode();

    String getMsgCode();

    boolean isEqual(String code);

    boolean isEqual(ErrorCode errorCode);
}
