package com.btaka.domain.web.controller;

import com.btaka.domain.web.dto.ResponseDTO;
import com.btaka.domain.web.dto.SignUpRequestDTO;
import com.btaka.domain.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/join")
public class BtakaSignUpApiController {

    private final UserService userService;

    @PostMapping("/signUp")
    public Mono<ResponseEntity<ResponseDTO>> signUp(@Validated @RequestBody SignUpRequestDTO signUpRequestDTO) {
        return userService.singUp(signUpRequestDTO)
                .flatMap(user -> Mono.just(ResponseEntity.ok(ResponseDTO.builder()
                                .set("oid", user.getOid())
                                .set("email", user.getEmail())
                                .set("userName", user.getUsername())
                                .build()))
                .doOnError(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage())));
    }

    @PostMapping("/check")
    public Mono<ResponseEntity<ResponseDTO>> checkUser(@NonNull @RequestBody String email) {
        return userService.checkUserEmail(email)
                .flatMap(isExist -> Mono.just(ResponseEntity.ok(ResponseDTO.builder().set("isExist", isExist).build())))
                .doOnError(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage()));
    }

}
