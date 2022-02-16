package com.btaka.domain.study.web;

import com.btaka.domain.study.dto.BoardDevStudyDTO;
import com.btaka.domain.study.service.BoardDevStudyReplyService;
import com.btaka.domain.study.service.BoardDevStudyService;
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
@RequestMapping("/api/v1/board/dev/study")
public class BoardDevStudyApiController {

    private final BoardDevStudyService boardDevStudyService;

    private final BoardDevStudyReplyService replyService;

    @GetMapping("/{oid}")
    public Mono<ResponseEntity<BoardResponseDTO>> get(@PathVariable("oid") @NonNull String oid) {
        return boardDevStudyService.get(oid)
                .flatMap(boardStudyDTO -> replyService.get(boardStudyDTO.getOid())
                        .map(replyDTOList -> new BoardResponseDTO(boardStudyDTO, replyDTOList)))
                .flatMap(dto -> Mono.just(ResponseEntity.ok(dto)))
                .doOnError(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage()));
    }

    @GetMapping("/list")
    public Mono<ResponseEntity<List<BoardDevStudyDTO>>> list() {
        return boardDevStudyService.list()
                .collectList()
                .flatMap(dto -> Mono.just(ResponseEntity.ok(dto)))
                .doOnError(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage()));
    }

    @PostMapping("/")
    public Mono<ResponseEntity<BoardDevStudyDTO>> add(@RequestBody BoardDevStudyDTO dto) {
        return boardDevStudyService.add(dto)
                .flatMap(savedDto -> Mono.just(ResponseEntity.ok(savedDto)))
                .doOnError(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage()));
    }

    @PutMapping("/")
    public Mono<ResponseEntity<BoardDevStudyDTO>> update(@RequestBody BoardDevStudyDTO dto) {
        return boardDevStudyService.update(dto)
                .flatMap(savedDto -> Mono.just(ResponseEntity.ok(savedDto)))
                .doOnError(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage()));
    }

    @DeleteMapping("/{oid}")
    public Flux<ResponseEntity<BoardDevStudyDTO>> delete(@PathVariable("oid") String oid) {
        return boardDevStudyService.delete(oid)
                .flatMap(dto -> Flux.just(ResponseEntity.ok(dto)))
                .doOnError(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage()));
    }



}
