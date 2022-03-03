package com.btaka.domain.study.repo;

import com.btaka.domain.study.entity.BoardDevStudyEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardDevStudyMongoRepository extends MongoRepository<BoardDevStudyEntity, String> {
}
