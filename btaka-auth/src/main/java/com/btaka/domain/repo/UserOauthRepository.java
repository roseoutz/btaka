package com.btaka.domain.repo;

import com.btaka.domain.entity.UserOauthEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserOauthRepository extends ReactiveMongoRepository<UserOauthEntity, String> {

    Mono<UserOauthEntity> findByUserOidAndOauthSite(String userOid, String oauthSite);

    Mono<UserOauthEntity> findByOauthId(String oauthId);
}
