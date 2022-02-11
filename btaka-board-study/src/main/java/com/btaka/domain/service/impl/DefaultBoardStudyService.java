package com.btaka.domain.service.impl;

import com.btaka.domain.dto.BoardStudyDTO;
import com.btaka.domain.repo.BoardStudyMongoRepository;
import com.btaka.domain.service.BoardStudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class DefaultBoardStudyService implements BoardStudyService {

    private final BoardStudyMongoRepository boardStudyMongoRepository;

    @Override
    public Mono<BoardStudyDTO> get(String oid) {
        return null;
    }

    @Override
    public Mono<BoardStudyDTO> add(BoardStudyDTO boardStudyDTO) {
        return null;
    }

    @Override
    public Mono<BoardStudyDTO> update(BoardStudyDTO boardStudyDTO) {
        return null;
    }

    @Override
    public Flux<BoardStudyDTO> delete(String oid) {
        return null;
    }

    @Override
    public Flux<BoardStudyDTO> list() {
        return null;
    }
}
