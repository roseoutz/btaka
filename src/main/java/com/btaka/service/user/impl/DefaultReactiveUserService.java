package com.btaka.service.user.impl;

import com.btaka.data.user.dto.UserDTO;
import com.btaka.data.user.entity.UserEntity;
import com.btaka.domain.user.UserMongoRepository;
import com.btaka.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DefaultReactiveUserService implements UserService {

    @Autowired
    private UserMongoRepository userMongoRepository;

    @Override
    public Mono<UserDTO> getUser(String userId) {
        return userMongoRepository.findByUserId(userId);
    }

    @Override
    public Flux<UserDTO> getAllUser() {
        return  null;
    }

    @Override
    public Mono<UserDTO> addUser(UserDTO userDto) {
        return userMongoRepository
                .save(new UserEntity(userDto))
                .map(UserDTO::new);
    }
}
