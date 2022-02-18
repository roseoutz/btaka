package com.btaka.cache.redis.service;

import reactor.core.publisher.Mono;

public interface RedisCacheService<D, ID> {

    Mono<D> save(D dto);

    Mono<D> get(ID id);

    void delete(ID id);

}
