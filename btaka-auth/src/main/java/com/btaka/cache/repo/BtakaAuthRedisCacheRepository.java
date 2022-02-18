package com.btaka.cache.repo;

import com.btaka.cache.entity.AuthCacheEntity;
import com.btaka.cache.redis.repository.RedisCacheRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BtakaAuthRedisCacheRepository extends RedisCacheRepository<AuthCacheEntity, String> {
}
