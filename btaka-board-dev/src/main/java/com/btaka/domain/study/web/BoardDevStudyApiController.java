package com.btaka.domain.study.web;

import com.btaka.board.common.page.DefaultPageResult;
import com.btaka.domain.study.dto.BoardDevStudyDTO;
import com.btaka.domain.study.service.BoardDevStudyReplyService;
import com.btaka.domain.study.service.BoardDevStudyService;
import com.btaka.dto.BoardListResponseDTO;
import com.btaka.dto.BoardResponseDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/board/dev/study")
public class BoardDevStudyApiController {

    private final BoardDevStudyService boardDevStudyService;

    private final BoardDevStudyReplyService replyService;

    @GetMapping("/{oid}")
    public Mono<ResponseEntity<BoardResponseDTO>> get(@PathVariable("oid") @NonNull String oid) {
        return boardDevStudyService.getDto(oid)
                .flatMap(boardDevStudyDTO -> replyService.get(boardDevStudyDTO.getOid())
                        .map(replyDTOList -> BoardResponseDTO.of(boardDevStudyDTO, DefaultPageResult.of(replyDTOList))))
                .flatMap(dto -> Mono.just(ResponseEntity.ok(dto)))
                .doOnError(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage()));
    }

    @GetMapping("/list")
    public Mono<ResponseEntity<BoardListResponseDTO>> list(@RequestParam(required = false) String page, @RequestParam(required = false) String pagePerSize) {
        return boardDevStudyService.list(PageRequest.of(page == null ? 1 : Integer.parseInt(page), pagePerSize == null ? 10 : Integer.parseInt(pagePerSize)))
                .map(ResponseEntity::ok)
                .doOnError(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage()));
    }

    @PostMapping("/")
    public Mono<ResponseEntity<BoardResponseDTO>> add(@RequestBody BoardDevStudyDTO dto) {
        return boardDevStudyService.add(dto)
                .flatMap(boardResponseDTO -> Mono.just(ResponseEntity.ok(boardResponseDTO))
                .doOnError(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage())));
    }

    @PutMapping("/")
    public Mono<ResponseEntity<BoardResponseDTO>> update(@RequestBody BoardDevStudyDTO dto) {
        return boardDevStudyService.update(dto)
                .flatMap(boardResponseDTO -> Mono.just(ResponseEntity.ok(boardResponseDTO))
                .doOnError(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage())));
    }

    @DeleteMapping("/{oid}")
    public Mono<ResponseEntity<BoardResponseDTO>> delete(@PathVariable("oid") String oid) {
        return boardDevStudyService.delete(oid)
                .then(Mono.just(ResponseEntity.ok(BoardResponseDTO.builder().build())))
                .doOnError(error -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage()));
    }



}
