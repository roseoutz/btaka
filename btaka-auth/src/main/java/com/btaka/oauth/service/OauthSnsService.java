package com.btaka.oauth.service;

import com.btaka.board.common.dto.SnsUser;
import reactor.core.publisher.Mono;

public interface OauthSnsService {

    String getAuthUrl(String state, String nonce);

    String getClientId();

    String getSite();

    Mono<SnsUser> auth(String code, String state, String nonce);

    Mono<SnsUser> userInfo(String token);

    Mono<SnsUser> register(String userOid, String code, String state, String nonce);
}
