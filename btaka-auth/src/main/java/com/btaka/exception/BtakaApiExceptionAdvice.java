package com.btaka.exception;

import com.btaka.domain.web.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class BtakaApiExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError().body(ResponseDTO.builder().success(false).error(e.getMessage()).errorMessage(e.getLocalizedMessage()).statusCode(500).build());
    }
}
