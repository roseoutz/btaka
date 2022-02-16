package com.btaka.domain.study.web;

import com.btaka.domain.study.dto.BoardDevStudyDTO;
import com.btaka.domain.study.dto.BoardDevStudyReplyDTO;
import com.btaka.domain.study.service.BoardDevStudyReplyService;
import com.btaka.domain.study.service.BoardDevStudyService;
import com.btaka.dto.BoardResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/board/dev/study/reply")
public class BoardDevStudyReplyApiController {

    private final BoardDevStudyReplyService boardDevStudyReplyService;

    private final BoardDevStudyService boardDevStudyService;

    @PostMapping("/")
    public Mono<ResponseEntity<BoardResponseDTO>> add(@RequestBody BoardDevStudyReplyDTO dto) {
        return boardDevStudyService.get(dto.getPostOid())
                .flatMap(boardDto ->
                        boardDevStudyReplyService.add(dto)
                                .map(boardStudyReplyDTOS -> new BoardResponseDTO(boardDto, boardStudyReplyDTOS))
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
