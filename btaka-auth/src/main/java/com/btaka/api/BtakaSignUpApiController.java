package com.btaka.api;

import com.btaka.domain.service.UserService;
import com.btaka.dto.*;
import com.btaka.security.service.LoginService;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/join")
public class BtakaSignUpApiController {

    private final UserService userService;

    @PostMapping("/signUp")
    public Mono<ResponseEntity<SignUpResponseDTO>> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        return userService.singUp(signUpRequestDTO)
                .flatMap(user -> Mono.just(user)
                        .map(info -> ResponseEntity.ok(SignUpResponseDTO.builder()
                                .oid(info.getOid())
                                .email(info.getEmail())
                                .userName(info.getUsername())
                                .build())
                        )
                )
                .doOnError(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage()));
    }

    @PostMapping("/check")
    public Mono<ResponseEntity<CheckEmailResponseDTO>> checkUser(@RequestBody CheckEmailRequestDTO emailRequestDTO) {
        return userService.checkUserEmail(emailRequestDTO.getEmail())
                .flatMap(isExist -> Mono.just(new CheckEmailResponseDTO(isExist))
                        .map(ResponseEntity::ok)
                )
                .doOnError(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage()));
    }

}
