package com.btaka.domain.web;

import com.btaka.domain.dto.BoardStudyDTO;
import com.btaka.domain.dto.BoardStudyReplyDTO;
import com.btaka.domain.service.BoardStudyReplyService;
import com.btaka.domain.service.BoardStudyService;
import com.btaka.dto.BoardResponseDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/board/study/reply")
public class BoardStudyReplyApiController {

    private final BoardStudyReplyService boardStudyReplyService;

    private final BoardStudyService boardStudyService;

    @PostMapping("/")
    public Mono<ResponseEntity<BoardResponseDTO>> add(@RequestBody BoardStudyReplyDTO dto) {
        return boardStudyReplyService.add(dto)
                .flatMap(boardStudyReplyDTOS ->
                    boardStudyService.get(dto.getPostOid())
                            .map(boardStudyDTO -> new BoardResponseDTO(boardStudyDTO, boardStudyReplyDTOS))
                )
                .flatMap(boardStudyDTO -> Mono.just(ResponseEntity.ok(boardStudyDTO)))
                .doOnError(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage()));
    }

    @PutMapping("/")
    public Mono<ResponseEntity<BoardStudyDTO>> update(@RequestBody BoardStudyReplyDTO dto) {
        return boardStudyReplyService.update(dto)
                .flatMap(boardStudyDTO -> Mono.just(ResponseEntity.ok(boardStudyDTO)))
                .doOnError(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage()));
    }

    @DeleteMapping("/{oid}")
    public Mono<ResponseEntity<BoardStudyDTO>> delete(@RequestBody BoardStudyReplyDTO dto) {
        return boardStudyReplyService.delete(dto)
                .flatMap(boardStudyDTO -> Mono.just(ResponseEntity.ok(boardStudyDTO)))
                .doOnError(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage()));
    }



}
