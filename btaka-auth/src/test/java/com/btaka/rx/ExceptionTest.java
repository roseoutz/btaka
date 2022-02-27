package com.btaka.rx;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ExceptionTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Test
    public void rxTest() {
        int a = 0;

        Mono.just(a)
                .map(integer -> {
                    logger.info(String.valueOf(integer));
                    Map<String, String> test = new ConcurrentHashMap<>();
                    System.out.println(test.get("aaa").toString());
                    return integer++;
                })
                .doOnError(throwable -> {
                    logger.info("hahahahahahaha");
                })
                .switchIfEmpty(Mono.error(new Exception("aaaaaa")))
                .subscribe();
    }
}
