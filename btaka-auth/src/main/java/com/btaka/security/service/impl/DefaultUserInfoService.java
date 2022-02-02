package com.btaka.security.service.impl;

import com.btaka.domain.repo.UserRepository;
import com.btaka.security.dto.UserInfo;
import com.btaka.security.service.UserInfoService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DefaultUserInfoService implements UserInfoService {

    private final UserRepository userRepository;

    public DefaultUserInfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Mono<UserDetails> findByUsername(String userId) {
        return userRepository.findByUserId(userId)
                .flatMap(entity -> Mono.justOrEmpty(UserInfo.toUserInfo(entity)));
    }
}
