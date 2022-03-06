package com.btaka.domain.study.web;

import com.btaka.board.common.page.DefaultPageResult;
import com.btaka.domain.study.dto.BoardDevStudyDTO;
import com.btaka.domain.study.dto.BoardDevStudyReplyDTO;
import com.btaka.domain.study.entity.BoardDevStudyEntity;
import com.btaka.domain.study.entity.BoardDevStudyReplyEntity;
import com.btaka.domain.study.service.BoardDevStudyReplyService;
import com.btaka.domain.study.service.BoardDevStudyService;
import com.btaka.dto.BoardResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/board/dev/study/reply")
public class BoardDevStudyReplyApiController {

    private final BoardDevStudyReplyService boardDevStudyReplyService;

    @PostMapping("/")
    public Mono<ResponseEntity<BoardResponseDTO>> add(@RequestBody BoardDevStudyReplyDTO dto) {
        return Mono.just(dto)
                .filter(replyDto -> Objects.isNull(dto.getParentOid()))
                .flatMap(boardDto ->
                        boardDevStudyReplyService.add(dto)
                                .map(boardStudyReplyDTOS -> BoardResponseDTO.of(DefaultPageResult.of(boardStudyReplyDTOS)))
                )
                .flatMap(boardStudyDTO -> Mono.just(ResponseEntity.ok(boardStudyDTO)))
                .doOnError(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage()));
    }

    @PutMapping("/")
    public Mono<ResponseEntity<BoardDevStudyDTO>> update(@RequestBody BoardDevStudyReplyDTO dto) {
        return boardDevStudyReplyService.update(dto)
                .flatMap(boardStudyDTO -> Mono.just(ResponseEntity.ok(boardStudyDTO)))
                .doOnError(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage()));
    }

    @DeleteMapping("/{oid}")
    public Mono<ResponseEntity<BoardDevStudyDTO>> delete(@RequestBody BoardDevStudyReplyDTO dto) {
        return boardDevStudyReplyService.delete(dto)
                .flatMap(boardStudyDTO -> Mono.just(ResponseEntity.ok(boardStudyDTO)))
                .doOnError(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage()));
    }



}
