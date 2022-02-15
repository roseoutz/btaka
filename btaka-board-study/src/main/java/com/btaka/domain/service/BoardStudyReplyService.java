package com.btaka.domain.service;

import com.btaka.domain.dto.BoardStudyDTO;
import com.btaka.domain.dto.BoardStudyReplyDTO;
import lombok.NonNull;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BoardStudyReplyService {

    Mono<List<BoardStudyReplyDTO>> get(@NonNull String postOid);

    Mono<List<BoardStudyReplyDTO>> add(BoardStudyReplyDTO dto);

    Mono<BoardStudyDTO> update(BoardStudyReplyDTO dto);

    Mono<BoardStudyDTO> delete(BoardStudyReplyDTO dto);

}
