package com.btaka.domain.service;

import com.btaka.board.common.dto.User;
import com.btaka.domain.web.dto.SignUpRequestDTO;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> findByOid(String oid);

    Mono<User> singUp(SignUpRequestDTO requestDTO);

    Mono<Boolean> checkUserEmail(String email);

    Mono<User> findByEmail(String email);

    Mono<User> updateUser(String oid, User user);

    Mono<User> changePassword(String oid, User user);

    Mono<Boolean> lockUser(User user, boolean isLock);

    Mono<Boolean> loginFail(User user);

    Mono<Boolean> loginSuccess(User user);
}

