package com.btaka.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class BtakaAuthApiController {

    @PostMapping("/api/user/info/auth")
    public Mono<ResponseEntity> auth() {
        return Mono.just(ResponseEntity.of(null));
    }

}
