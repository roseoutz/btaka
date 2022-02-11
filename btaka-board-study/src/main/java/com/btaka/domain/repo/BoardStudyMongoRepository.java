package com.btaka.domain.repo;

import com.btaka.domain.entity.BoardStudyEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardStudyMongoRepository extends ReactiveMongoRepository<BoardStudyEntity, String> {
}
