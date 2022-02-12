package com.btaka.domain.repo;

import com.btaka.domain.entity.BoardStudyReplyEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BoardStudyReplyMongoRepository extends ReactiveMongoRepository<BoardStudyReplyEntity, String> {

    Flux<BoardStudyReplyEntity> findByParentOid(String parentOid);
    Mono<BoardStudyReplyEntity> findByOidAndParentOid(String oid, String parentOid);
}
