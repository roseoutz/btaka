package com.btaka.domain.free.repo;

import com.btaka.domain.free.entity.BoardDevFreeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardDevFreeMongoRepository extends JpaRepository<BoardDevFreeEntity, String> {
}
