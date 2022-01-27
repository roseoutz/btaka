package com.btaka.service.user;

import com.btaka.common.dto.SearchParam;
import com.btaka.data.user.dto.UserDTO;
import com.btaka.dto.ResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<ResponseDTO> getUser(String oid);

    Mono<ResponseDTO> getUserByUserId(String userId);

    Flux<ResponseDTO> searchUser(SearchParam searchParam);

    Mono<ResponseDTO> addUser(UserDTO userDto);

    Mono<ResponseDTO> updateUser(UserDTO userDTO);

    Mono<Void> deleteUser(String oid);
}
