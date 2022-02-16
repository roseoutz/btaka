package com.btaka.domain.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@NoRepositoryBean
public interface AbstractBoardReplyRepository<T, ID> extends ReactiveMongoRepository<T, ID> {

    Mono<T> findByOidAndParentOid(String oid, String parentOid);

    Flux<T> findAllByPostOid(String postOid);

    Flux<T> findAllByParentOidAndPostOid(String parentOid, String postOid);
}
