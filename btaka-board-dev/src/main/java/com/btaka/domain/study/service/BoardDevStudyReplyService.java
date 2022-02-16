package com.btaka.domain.study.service;

import com.btaka.domain.study.dto.BoardDevStudyDTO;
import com.btaka.domain.study.dto.BoardDevStudyReplyDTO;
import lombok.NonNull;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BoardDevStudyReplyService {

    Mono<List<BoardDevStudyReplyDTO>> get(@NonNull String postOid);

    Mono<List<BoardDevStudyReplyDTO>> add(BoardDevStudyReplyDTO dto);

    Mono<BoardDevStudyDTO> update(BoardDevStudyReplyDTO dto);

    Mono<BoardDevStudyDTO> delete(BoardDevStudyReplyDTO dto);

}
