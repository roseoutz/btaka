package com.btaka.domain.study.repo;

import com.btaka.domain.repo.AbstractBoardReplyRepository;
import com.btaka.domain.study.entity.BoardDevStudyReplyEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BoardDevStudyReplyMongoRepository extends AbstractBoardReplyRepository<BoardDevStudyReplyEntity, String> {
}
