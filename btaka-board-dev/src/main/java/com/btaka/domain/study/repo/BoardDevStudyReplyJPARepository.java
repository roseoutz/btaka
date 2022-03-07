package com.btaka.domain.study.repo;

import com.btaka.domain.study.entity.BoardDevStudyReplyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardDevStudyReplyJPARepository extends JpaRepository<BoardDevStudyReplyEntity, String> {

    List<BoardDevStudyReplyEntity> findAllByInsertUser(String insertUser, Pageable pageable);

    List<BoardDevStudyReplyEntity> findAllByPostOid(String postOid);

    BoardDevStudyReplyEntity findByOidAndPostOid(String oid, String postOid);

    @Query("select r from btaka_board_dev_study_reply r left join fetch r.parent where r.post.oid = :postOid order by r.parent.oid asc nulls first, r.insertTime asc")
    List<BoardDevStudyReplyEntity> findWithByInsertUserAndParentByPostOidAscNullsFirstReplyInsertTimeAsc(String postOid);
}
