package com.btaka.cache.service;

import com.btaka.cache.dto.AuthCacheDTO;
import com.btaka.cache.dto.AuthInfo;
import com.btaka.cache.entity.AuthCacheEntity;
import com.btaka.cache.redis.service.AbstractRedisCacheService;
import com.btaka.cache.repo.BtakaAuthRedisCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class BtakaAuthRedisCacheService extends AbstractRedisCacheService<String, AuthCacheEntity, AuthCacheDTO>  implements AuthCacheService{

    @Autowired
    private BtakaAuthRedisCacheRepository authRedisCacheRepository;


    public BtakaAuthRedisCacheService() {
        super(AuthCacheDTO.class, AuthCacheEntity.class);
    }

    @Override
    protected BtakaAuthRedisCacheRepository getRepository() {
        return authRedisCacheRepository;
    }

    @Override
    public Mono<AuthCacheDTO> saveAuthInfo(String sessionId, AuthInfo authInfo) {
        return Mono.just(authInfo)
                .publishOn(Schedulers.boundedElastic())
                .map(auth -> {
                    AuthCacheDTO authCacheDTO = AuthCacheDTO.builder().authInfo(authInfo).sid(sessionId).build();

                    return authRedisCacheRepository.save(toEntity(authCacheDTO));
                })
                .flatMap(authCacheEntity -> Mono.just(authCacheEntity)
                        .map(this::toDto)
                );
    }
}
