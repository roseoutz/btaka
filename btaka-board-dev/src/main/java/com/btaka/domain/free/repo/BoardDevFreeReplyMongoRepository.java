package com.btaka.domain.free.repo;

import com.btaka.domain.free.entity.BoardDevFreeReplyEntity;
import com.btaka.domain.repo.AbstractBoardReplyRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardDevFreeReplyMongoRepository extends AbstractBoardReplyRepository<BoardDevFreeReplyEntity, String> {

}
