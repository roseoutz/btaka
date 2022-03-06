package com.btaka.domain.free.repo;

import com.btaka.domain.free.entity.BoardDevFreeReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardDevFreeReplyMongoRepository extends JpaRepository<BoardDevFreeReplyEntity, String> {

}
