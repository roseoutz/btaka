package com.btaka.domain.web;

import com.btaka.cache.service.AuthCacheService;
import com.btaka.dto.AuthRequestDTO;
import com.btaka.dto.AuthResponseDTO;
import com.btaka.security.service.LoginService;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class BtakaAuthApiController {

    private final LoginService loginService;

    @PostMapping
    public Mono<ResponseEntity<AuthResponseDTO>> auth(@RequestBody AuthRequestDTO authRequestDTO, ServerWebExchange webExchange) {
        return Mono.just(authRequestDTO)
                .filter(dto -> !StringUtil.isNullOrEmpty(dto.getEmail()) || !StringUtil.isNullOrEmpty(dto.getPassword()))
                .flatMap(dto -> loginService.auth(webExchange, authRequestDTO))
                .flatMap(authRequestDTOMono -> Mono.just(authRequestDTOMono)
                        .map(ResponseEntity::ok)
                )
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()))
                .doOnError(throwable -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(throwable.getMessage())));
    }


    @PostMapping("/logout")
    public Mono<ResponseEntity> logout() {
        return Mono.just(ResponseEntity.of(null));
    }

    @GetMapping("/")
    public Mono<ResponseEntity<AuthResponseDTO>> isLogin(@CookieValue(value = "psid") String sessionId) {
        return Mono.just(sessionId)
                .filter(psid -> !Objects.isNull(psid))
                .flatMap(psid -> loginService.isLogin(psid))
                .flatMap(authResponseDTO -> Mono.just(authResponseDTO)
                        .map(response -> ResponseEntity.ok(authResponseDTO))
                ).switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));

    }
}
