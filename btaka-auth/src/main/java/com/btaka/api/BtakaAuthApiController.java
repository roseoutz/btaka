package com.btaka.api;

import com.btaka.dto.AuthRequestDTO;
import com.btaka.dto.AuthResponseDTO;
import com.btaka.security.service.LoginService;
import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class BtakaAuthApiController {

    private LoginService loginService;

    @PostMapping("/")
    public Mono<ResponseEntity<AuthResponseDTO>> auth(@RequestBody AuthRequestDTO authRequestDTO) {
        return Mono.just(authRequestDTO)
                .filter(dto -> !StringUtil.isNullOrEmpty(dto.getUserId()) || !StringUtil.isNullOrEmpty(dto.getPassword()))
                .flatMap(dto -> loginService.auth(dto))
                .flatMap(authRequestDTOMono -> Mono.just(authRequestDTOMono)
                        .map(ResponseEntity::ok))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @PostMapping("/logout")
    public Mono<ResponseEntity> logout() {
        return Mono.just(ResponseEntity.of(null));
    }
}
