package com.btaka.exception;

import com.btaka.common.exception.AbstractApiExceptionAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class BtakaApiExceptionAdvice extends AbstractApiExceptionAdvice {


}
