package com.btaka.domain.repo;

import com.btaka.domain.entity.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<UserEntity, String> {

    Mono<UserEntity> findByEmail(String email);

    Mono<UserEntity> findByOid(String oid);

}