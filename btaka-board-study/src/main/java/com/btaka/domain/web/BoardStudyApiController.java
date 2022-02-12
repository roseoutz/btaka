package com.btaka.domain.web;

import com.btaka.domain.dto.BoardStudyDTO;
import com.btaka.domain.service.BoardStudyService;
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
@RequestMapping("/api/v1/board/study")
public class BoardStudyApiController {

    private final BoardStudyService boardStudyService;

    @GetMapping("/{oid}")
    public Mono<ResponseEntity<BoardStudyDTO>> get(@PathVariable("oid") @NonNull String oid) {
        return boardStudyService.get(oid)
                .flatMap(dto -> Mono.just(ResponseEntity.ok(dto)))
                .doOnError(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage())));
    }

    @GetMapping("/list")
    public Mono<ResponseEntity<List<BoardStudyDTO>>> list() {
        return boardStudyService.list()
                .collectList()
                .flatMap(dto -> Mono.just(ResponseEntity.ok(dto)))
                .doOnError(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage())));
    }

    @PostMapping("/")
    public Mono<ResponseEntity<BoardStudyDTO>> add(@RequestBody BoardStudyDTO dto) {
        return boardStudyService.add(dto)
                .flatMap(savedDto -> Mono.just(ResponseEntity.ok(savedDto)))
                .doOnError(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage())));
    }

    @PutMapping("/")
    public Mono<ResponseEntity<BoardStudyDTO>> update(@RequestBody BoardStudyDTO dto) {
        return boardStudyService.update(dto)
                .flatMap(savedDto -> Mono.just(ResponseEntity.ok(savedDto)))
                .doOnError(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage())));
    }

    @DeleteMapping("/{oid}")
    public Flux<ResponseEntity<BoardStudyDTO>> delete(@PathVariable("oid") String oid) {
        return boardStudyService.delete(oid)
                .flatMap(dto -> Flux.just(ResponseEntity.ok(dto)))
                .doOnError(error -> Flux.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage())));
    }



}
