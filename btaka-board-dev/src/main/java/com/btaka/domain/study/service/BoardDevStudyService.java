package com.btaka.domain.study.service;

import com.btaka.board.common.page.PageResult;
import com.btaka.domain.study.dto.BoardDevStudyDTO;
import com.btaka.dto.BoardListResponseDTO;
import com.btaka.dto.BoardResponseDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BoardDevStudyService {

    Mono<BoardDevStudyDTO> getDto(String oid);

    Mono<BoardResponseDTO> get(String oid);

    Mono<BoardResponseDTO> add(BoardDevStudyDTO boardDevStudyDTO);

    Mono<BoardResponseDTO> update(BoardDevStudyDTO boardDevStudyDTO);

    Mono<Void> delete(String oid);

    Mono<BoardListResponseDTO> list(Pageable pageable);


}
