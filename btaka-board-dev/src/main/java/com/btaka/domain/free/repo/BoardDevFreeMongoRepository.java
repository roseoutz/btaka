package com.btaka.domain.free.repo;

import com.btaka.domain.free.entity.BoardDevFreeEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardDevFreeMongoRepository extends ReactiveMongoRepository<BoardDevFreeEntity, String> {
}
