package com.btaka.cache.redis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RedisCacheRepository<T, ID> extends CrudRepository<T, ID> {
}
