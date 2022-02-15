package com.btaka.domain.service.impl;

import com.btaka.domain.dto.BoardStudyDTO;
import com.btaka.domain.dto.BoardStudyReplyDTO;
import com.btaka.domain.entity.BoardStudyReplyEntity;
import com.btaka.domain.repo.BoardStudyReplyMongoRepository;
import com.btaka.domain.service.BoardStudyReplyService;
import com.btaka.domain.service.BoardStudyService;
import io.netty.util.internal.StringUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service("defaultBoardStudyReplyService")
public class DefaultBoardStudyReplyService implements BoardStudyReplyService {

    private final BoardStudyReplyMongoRepository replyMongoRepository;

    private final BoardStudyService boardStudyService;

    @Override
    public Mono<List<BoardStudyReplyDTO>> get(@NonNull String postOid) {
        return Mono.just(postOid)
                .filter(oid -> !Objects.isNull(oid))
                .flatMap(oid -> replyMongoRepository.findAllByPostOid(oid)
                        .map(BoardStudyReplyDTO::new)
                        .collectList()
                )
                .switchIfEmpty(Mono.just(List.of(new BoardStudyReplyDTO())));
    }

    @Override
    public Mono<List<BoardStudyReplyDTO>> add(BoardStudyReplyDTO boardStudyReplyDTO) {
        return Mono.just(new BoardStudyReplyEntity(boardStudyReplyDTO))
                .filter(entity -> entity.getParentOid() != null && entity.getPostOid() != null)
                .flatMap(replyMongoRepository::save)
                .flatMap(savedEntity -> get(boardStudyReplyDTO.getPostOid()));
    }

    @Override
    public Mono<BoardStudyDTO> update(BoardStudyReplyDTO dto) {
        return replyMongoRepository.findByOidAndParentOid(dto.getOid(), dto.getParentOid())
                .doOnNext(savedEntity -> update(dto, savedEntity))
                .then(boardStudyService.get(dto.getParentOid()));
    }

    private Mono<BoardStudyReplyEntity> update(BoardStudyReplyDTO dto, BoardStudyReplyEntity savedEntity) {
        if (!StringUtil.isNullOrEmpty(dto.getReply())) savedEntity.setReply(dto.getReply());

        return Mono.just(savedEntity);
    }

    @Override
    public Mono<BoardStudyDTO> delete(BoardStudyReplyDTO dto) {
        return replyMongoRepository.deleteById(dto.getOid())
                .then(boardStudyService.get(dto.getParentOid()));
    }

}
