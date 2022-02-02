package com.btaka.domain.repo;

import com.btaka.domain.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserEntity, String> {

    Mono<UserEntity> findByUserId(String userId);

    Mono<UserEntity> findByOid(String oid);

    Mono<UserEntity> findByOauthId(String oauthId);
}