package com.btaka.domain.study.service;

import com.btaka.domain.study.dto.BoardDevStudyDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BoardDevStudyService {

    Mono<BoardDevStudyDTO> get(String oid);

    Mono<BoardDevStudyDTO> add(BoardDevStudyDTO boardDevStudyDTO);

    Mono<BoardDevStudyDTO> update(BoardDevStudyDTO boardDevStudyDTO);

    Flux<BoardDevStudyDTO> delete(String oid);

    Flux<BoardDevStudyDTO> list();


}
