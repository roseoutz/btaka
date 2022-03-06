package com.btaka.domain.study.repo;

import com.btaka.domain.study.entity.BoardDevStudyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardDevStudyJPARepository extends JpaRepository<BoardDevStudyEntity, String> {
}
