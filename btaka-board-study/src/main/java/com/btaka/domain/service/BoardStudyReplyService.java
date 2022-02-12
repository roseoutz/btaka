package com.btaka.domain.service;

import com.btaka.domain.dto.BoardStudyDTO;
import com.btaka.domain.dto.BoardStudyReplyDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BoardStudyReplyService {

    Mono<BoardStudyDTO> add(BoardStudyReplyDTO dto);

    Mono<BoardStudyDTO> update(BoardStudyReplyDTO dto);

    Mono<BoardStudyDTO> delete(BoardStudyReplyDTO dto);

}
