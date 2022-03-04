package com.btaka.domain.web.controller;

import com.btaka.board.common.dto.ResponseDTO;
import com.btaka.domain.web.dto.AuthRequestDTO;
import com.btaka.domain.service.LoginService;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class BtakaAuthApiController {

    private final LoginService loginService;

    @PostMapping("/process")
    public Mono<ResponseEntity<ResponseDTO>> auth(@CookieValue(value = "psid", required = false) String sessionId, @RequestBody AuthRequestDTO authRequestDTO, ServerWebExchange webExchange) {
        return loginService.isLogin(sessionId)
                .publishOn(Schedulers.single())
                .filter(ResponseDTO::isSuccess)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(authRequestDTO)
                        .filter(dto -> !StringUtil.isNullOrEmpty(dto.getEmail()) || !StringUtil.isNullOrEmpty(dto.getPassword()))
                        .flatMap(dto -> loginService.auth(webExchange, authRequestDTO)
                                .map(responseDTO -> ResponseEntity.ok().body(responseDTO))
                        )
                );
    }


    @PostMapping("/logout")
    public Mono<ResponseEntity> logout() {
        return Mono.just(ResponseEntity.of(null));
    }

    @GetMapping("/")
    public Mono<ResponseEntity<ResponseDTO>> isLogin(@CookieValue(value = "psid", required = false) String sessionId) {
        return Mono.just(sessionId)
                .filter(psid -> !Objects.isNull(psid))
                .flatMap(psid -> loginService.isLogin(psid)
                            .map(ResponseEntity::ok)
                )
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));

    }
}
