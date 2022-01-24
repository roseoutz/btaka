package com.btaka.service.user;

import com.btaka.common.dto.SearchParam;
import com.btaka.data.user.dto.UserDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserDTO> getUser(String oid);

    Mono<UserDTO> getUserByUserId(String userId);

    Flux<UserDTO> searchUser(SearchParam searchParam);

    Mono<UserDTO> addUser(UserDTO userDto);

    Mono<UserDTO> updateUser(UserDTO userDTO);

    Mono<Void> deleteUser(String oid);
}
