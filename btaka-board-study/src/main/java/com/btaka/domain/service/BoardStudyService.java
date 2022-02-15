package com.btaka.domain.service;

import com.btaka.domain.dto.BoardStudyDTO;
import com.btaka.domain.dto.BoardStudyReplyDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BoardStudyService {

    Mono<BoardStudyDTO> get(String oid);

    Mono<BoardStudyDTO> add(BoardStudyDTO boardStudyDTO);

    Mono<BoardStudyDTO> update(BoardStudyDTO boardStudyDTO);

    Flux<BoardStudyDTO> delete(String oid);

    Flux<BoardStudyDTO> list();


}
