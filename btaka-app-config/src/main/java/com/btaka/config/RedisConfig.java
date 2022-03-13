package com.btaka.config;

import com.btaka.cache.redis.config.AbstractRedisCacheConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableRedisRepositories
public class RedisConfig extends AbstractRedisCacheConfig {

    @Value(value = "${btaka.cache.default.expired.time:600}")
    private long DEFAULT_CACHE_EXPIRED_TIME = 60 * 10L;

    @Value(value = "${btaka.cache.auth.cache.expired.time:3600}")
    private long AUTH_CACHE_EXPIRED_TIME = 60 * 60 * 1L;

    @Value(value = "${btaka.cache.auth.name:batakaAuthInfo}")
    private String AUTH_CACHE_KEY = "btakaAuthInfo";

    @Override
    protected Map<String, RedisCacheConfiguration> getCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> cacheConfigurationMap = new ConcurrentHashMap<>();

        cacheConfigurationMap.put(AUTH_CACHE_KEY, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(AUTH_CACHE_EXPIRED_TIME)));

        return cacheConfigurationMap;
    }

    @Override
    protected RedisConnectionFactory getRedisConnectionFactory() {
        return lettuceConnectionFactory();
    }

    @Override
    protected Long getCacheDefaultTtl() {
        return DEFAULT_CACHE_EXPIRED_TIME;
    }
}
