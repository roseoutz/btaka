package com.btaka.domain.web.controller;

import com.btaka.domain.web.dto.AuthRequestDTO;
import com.btaka.domain.web.dto.ResponseDTO;
import com.btaka.domain.service.LoginService;
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

    @PostMapping("/process")
    public Mono<ResponseEntity<ResponseDTO>> auth(@RequestBody AuthRequestDTO authRequestDTO, ServerWebExchange webExchange) {
        return Mono.just(webExchange)
                .map(exchange -> exchange.getRequest().getCookies().get("psid")
                        .stream().filter(Objects::nonNull).findAny())
                .flatMap(httpCookie ->
                        loginService.isLogin(httpCookie.get().getValue())
                                .filter(responseDTO -> responseDTO.isSuccess())
                                .flatMap(responseDTO -> Mono.just(ResponseEntity.ok(responseDTO)))
                                .switchIfEmpty(Mono.just(authRequestDTO)
                                        .filter(dto -> !StringUtil.isNullOrEmpty(dto.getEmail()) || !StringUtil.isNullOrEmpty(dto.getPassword()))
                                        .flatMap(dto -> loginService.auth(webExchange, authRequestDTO)
                                        .flatMap(d -> Mono.just(d).map(md -> ResponseEntity.ok(md))))
                .doOnError(throwable -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(throwable.getMessage()))));
    }


    @PostMapping("/logout")
    public Mono<ResponseEntity> logout() {
        return Mono.just(ResponseEntity.of(null));
    }

    @GetMapping("/")
    public Mono<ResponseEntity<ResponseDTO>> isLogin(@CookieValue(value = "psid") String sessionId) {
        return Mono.just(sessionId)
                .filter(psid -> !Objects.isNull(psid))
                .flatMap(loginService::isLogin)
                .flatMap(authResponseDTO -> Mono.just(authResponseDTO)
                        .map(response -> ResponseEntity.ok(authResponseDTO))
                ).switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));

    }
}
