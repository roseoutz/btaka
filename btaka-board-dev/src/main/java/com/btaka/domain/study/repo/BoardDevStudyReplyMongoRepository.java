package com.btaka.domain.study.repo;

import com.btaka.domain.study.entity.BoardDevStudyReplyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardDevStudyReplyMongoRepository extends MongoRepository<BoardDevStudyReplyEntity, String> {

    List<BoardDevStudyReplyEntity> findAllByInsertUser(String insertUser, Pageable pageable);

    List<BoardDevStudyReplyEntity> findAllByPostOid(String postOid);

    BoardDevStudyReplyEntity findByOidAndPostOid(String oid, String postOid);
}
