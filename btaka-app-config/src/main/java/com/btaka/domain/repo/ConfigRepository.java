package com.btaka.domain.repo;

import com.btaka.domain.entity.ConfigEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ConfigRepository extends ReactiveMongoRepository<ConfigEntity, String> {

    Mono<ConfigEntity> findByKey(String key);


}