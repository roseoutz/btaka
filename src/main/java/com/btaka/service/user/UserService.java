package com.btaka.service.user;

import com.btaka.data.user.dto.UserDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserDTO> getUser(String userId);

    Flux<UserDTO> getAllUser();

    Mono<UserDTO> addUser(UserDTO userDto);
}
