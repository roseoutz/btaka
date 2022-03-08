package com.btaka.domain.web.controller;

import com.btaka.board.common.dto.ResponseDTO;
import com.btaka.constant.UserParamConst;
import com.btaka.domain.web.dto.SignUpRequestDTO;
import com.btaka.domain.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
                                .set(UserParamConst.PARAM_USER_OBJECT_ID.getKey(), user.getOid())
                                .set(UserParamConst.PARAM_USER_EMAIL.getKey(), user.getEmail())
                                .set(UserParamConst.PARAM_USER_NAME.getKey(), user.getUsername())
                                .build())));
    }

    @PostMapping("/check")
    public Mono<ResponseEntity<ResponseDTO>> checkUser(@NonNull @RequestBody String email) {
        return userService.checkUserEmail(email)
                .flatMap(isExist -> Mono.just(ResponseEntity.ok(ResponseDTO.builder()
                        .set(UserParamConst.PARAM_USER_IS_EXIST.getKey(), isExist)
                        .build())));
    }

}
