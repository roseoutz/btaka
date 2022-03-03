package com.btaka.domain.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@NoRepositoryBean
public interface AbstractBoardReplyRepository<T, ID> extends MongoRepository<T, ID> {

    T findByOidAndParentOid(String oid, String parentOid);

    T findAllByPostOid(String postOid);

    T findAllByParentOidAndPostOid(String parentOid, String postOid);
}
