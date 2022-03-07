package com.btaka.cache.repo;

import com.btaka.cache.entity.AuthCacheEntity;
import com.btaka.cache.redis.repository.RedisCacheRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BtakaAuthRedisCacheRepository extends RedisCacheRepository<AuthCacheEntity, String> {

    Optional<AuthCacheEntity> findByAccessToken(String accessToken);

    void deleteBySid(String sid);
}
