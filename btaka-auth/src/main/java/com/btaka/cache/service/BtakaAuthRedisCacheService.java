package com.btaka.cache.service;

import com.btaka.cache.dto.AuthCacheDTO;
import com.btaka.cache.dto.AuthInfo;
import com.btaka.cache.entity.AuthCacheEntity;
import com.btaka.cache.redis.service.AbstractRedisCacheService;
import com.btaka.cache.repo.BtakaAuthRedisCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import springfox.documentation.annotations.Cacheable;

import java.time.LocalDateTime;
import java.util.Optional;

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

    @Transactional
    @Override
    public Mono<AuthCacheDTO> saveAuthInfo(String sessionId, AuthInfo authInfo) {
        AuthCacheEntity savedCacheEntity = authRedisCacheRepository.save(toEntity(AuthCacheDTO.builder().authInfo(authInfo).sid(sessionId).build()));


        return Mono.just(toDto(savedCacheEntity));
    }

    @Override
    public Mono<AuthCacheDTO> isLogin(String sessionId) {
        Optional<AuthCacheEntity> authCacheEntity = authRedisCacheRepository.findById(sessionId);

        if (authCacheEntity.isPresent()) {
            return Mono.just(authCacheEntity.get())
                    .filter(entity -> LocalDateTime.now().isAfter(entity.getAuthInfo().getExpiredAt()))
                    .map(entity -> toDto(entity));
        } else {
            return Mono.just(new AuthCacheDTO());
        }
    }
}
