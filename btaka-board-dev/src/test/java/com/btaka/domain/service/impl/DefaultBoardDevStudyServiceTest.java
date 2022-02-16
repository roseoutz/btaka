package com.btaka.domain.service.impl;

import com.btaka.domain.study.dto.BoardDevStudyDTO;
import com.btaka.domain.study.entity.BoardDevStudyEntity;
import com.btaka.domain.study.repo.BoardDevStudyMongoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import reactor.core.publisher.Mono;

import java.util.List;

@SpringBootTest
class DefaultBoardDevStudyServiceTest {

    @Autowired
    private BoardDevStudyMongoRepository boardDevStudyMongoRepository;

    @Test
    void get() {
    }

    @Test
    // @Transactional
    @Rollback(value = false)
    void add() {
        BoardDevStudyDTO boardDevStudyDTO = BoardDevStudyDTO.builder()
                .contents("hahahahahaha")
                .title("hahahahahaha")
                .hashTags(List.of("#jaja", "#kekek", "#lala", "#qooqq"))
                .insertUser("ihihie")
                .build();

        Mono<BoardDevStudyEntity> entityMono = boardDevStudyMongoRepository.save(new BoardDevStudyEntity(boardDevStudyDTO));

        entityMono.subscribe(entity -> System.out.println(entity.toString()));
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void list() {
    }
}