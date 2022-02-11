package com.btaka.domain.service.impl;

import com.btaka.domain.dto.BoardStudyDTO;
import com.btaka.domain.entity.BoardStudyEntity;
import com.btaka.domain.repo.BoardStudyMongoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DefaultBoardStudyServiceTest {

    @Autowired
    private BoardStudyMongoRepository boardStudyMongoRepository;

    @Test
    void get() {
    }

    @Test
    // @Transactional
    @Rollback(value = false)
    void add() {
        BoardStudyDTO boardStudyDTO = BoardStudyDTO.builder()
                .contents("hahahahahaha")
                .title("hahahahahaha")
                .hashTags(List.of("#jaja", "#kekek", "#lala", "#qooqq"))
                .insertUser("ihihie")
                .build();

        Mono<BoardStudyEntity> entityMono = boardStudyMongoRepository.save(new BoardStudyEntity(boardStudyDTO));

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