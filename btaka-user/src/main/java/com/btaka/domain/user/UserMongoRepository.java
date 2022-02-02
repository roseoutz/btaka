package com.btaka.domain.user;

import com.btaka.data.user.entity.UserInfoEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserMongoRepository extends ReactiveMongoRepository<UserInfoEntity, String> {

    Mono<UserInfoEntity> findByUserId(String userId);



}
