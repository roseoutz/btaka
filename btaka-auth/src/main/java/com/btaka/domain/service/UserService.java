package com.btaka.domain.service;

import com.btaka.board.common.dto.User;
import com.btaka.dto.SignUpRequestDTO;
import com.btaka.security.dto.UserInfo;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> singUp(SignUpRequestDTO requestDTO);

    Mono<Boolean> checkUserEmail(String email);

    Mono<UserInfo> findByEmail(String email);
}
