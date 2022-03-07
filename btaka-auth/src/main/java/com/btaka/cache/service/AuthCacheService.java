package com.btaka.cache.service;

import com.btaka.cache.dto.AuthCacheDTO;
import com.btaka.cache.dto.AuthInfo;
import com.btaka.cache.redis.service.RedisCacheService;
import reactor.core.publisher.Mono;

public interface AuthCacheService extends RedisCacheService<AuthCacheDTO, String> {

    Mono<AuthCacheDTO> saveAuthInfo(String sessionId, AuthInfo authInfo);

    Mono<AuthCacheDTO> isLogin(String sessionId);

    Mono<AuthCacheDTO> isTokenAvailable(String accessToken);

    Mono<Boolean> expireToken(String sessionId);
}
