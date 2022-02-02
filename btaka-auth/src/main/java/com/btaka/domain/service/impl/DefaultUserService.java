package com.btaka.domain.service.impl;

import com.btaka.domain.repo.UserRepository;
import com.btaka.domain.service.UserService;
import com.btaka.security.dto.UserInfo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UserInfo> findByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .flatMap(entity -> Mono.just(UserInfo.toUserInfo(entity)));
    }
}
