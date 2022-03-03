package com.btaka.common.exception;

import com.btaka.board.common.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public abstract class AbstractApiExceptionAdvice {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(BtakaException.class)
    public ResponseEntity<ResponseDTO> btakaExceptionHandler(BtakaException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .internalServerError()
                .body(ResponseDTO.builder()
                        .success(false)
                        .error(e.getMessage())
                        .errorMessage(e.getLocalizedMessage())
                        .statusCode(e.getStatusCode())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> exceptionHandler(Exception e) {
        BtakaException be = new BtakaException(e);
        log.error(be.getMessage(), be);
        return ResponseEntity
                .internalServerError()
                .body(ResponseDTO.builder()
                        .success(false)
                        .error(be.getMessage())
                        .errorMessage(be.getLocalizedMessage())
                        .statusCode(be.getStatusCode())
                        .build());
    }
}
