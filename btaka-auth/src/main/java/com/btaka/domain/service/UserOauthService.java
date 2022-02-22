package com.btaka.domain.service;

import com.btaka.domain.service.dto.UserOauthDTO;
import reactor.core.publisher.Mono;

public interface UserOauthService {

    Mono<UserOauthDTO> get(String oauthSite, String userOid);

    Mono<UserOauthDTO> getByOauthId(String oauthId);

    Mono<UserOauthDTO> save(UserOauthDTO userOauthDTO);

}
