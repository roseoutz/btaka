package com.btaka.domain.service;

import com.btaka.security.dto.UserInfo;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserInfo> findByUserId(String userId);
}
