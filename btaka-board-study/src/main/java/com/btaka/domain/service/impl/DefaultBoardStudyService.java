package com.btaka.domain.service.impl;

import com.btaka.domain.dto.BoardStudyDTO;
import com.btaka.domain.dto.BoardStudyReplyDTO;
import com.btaka.domain.entity.BoardStudyEntity;
import com.btaka.domain.entity.BoardStudyReplyEntity;
import com.btaka.domain.repo.BoardStudyMongoRepository;
import com.btaka.domain.repo.BoardStudyReplyMongoRepository;
import com.btaka.domain.service.BoardStudyService;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service("defaultBoardStudyService")
public class DefaultBoardStudyService implements BoardStudyService {

    private final BoardStudyMongoRepository boardStudyMongoRepository;

    private final BoardStudyReplyMongoRepository replyMongoRepository;

    @Override
    public Mono<BoardStudyDTO> get(String oid) {
        return getByOid(oid)
                .flatMap(boardStudyEntity -> {
                    boardStudyEntity.setViews(boardStudyEntity.getViews() + 1);
                    return update(boardStudyEntity);
                })
                .flatMap(boardStudyEntity ->
                        Mono.just(boardStudyEntity)
                                .map(entity -> new BoardStudyDTO(entity))
                ).switchIfEmpty(Mono.just(new BoardStudyDTO()));
    }

    private Mono<BoardStudyEntity> getByOid(String oid) {
        return boardStudyMongoRepository.findById(oid)
                .switchIfEmpty(Mono.just(new BoardStudyEntity()));
    }

    @Override
    public Mono<BoardStudyDTO> add(BoardStudyDTO boardStudyDTO) {
        return Mono.just(new BoardStudyEntity(boardStudyDTO))
                .flatMap(entity -> boardStudyMongoRepository.save(entity))
                .flatMap(entity ->
                        Mono.just(entity)
                                .map(savedEntity -> new BoardStudyDTO(savedEntity))
                );
    }

    @Override
    @Transactional
    public Mono<BoardStudyDTO> addReply(BoardStudyReplyDTO boardStudyReplyDTO) {
        return Mono.just(boardStudyReplyDTO)
                .flatMap(dto ->
                        get(boardStudyReplyDTO.getParentOid())
                                .filter(boardStudyDTO -> boardStudyDTO != null)
                                .flatMap(boardStudyDTO -> Mono.just(new BoardStudyEntity(boardStudyDTO))
                                        .flatMap(entity -> {
                                            entity.addReply(new BoardStudyReplyEntity(boardStudyReplyDTO));
                                            return boardStudyMongoRepository.save(entity);
                                            // return entity;
                                        }))
                )
                .flatMap(entity ->
                    Mono.just(entity)
                            .map(boardStudyEntity -> new BoardStudyDTO(boardStudyEntity))
                );
    }

    @Override
    @Transactional
    public Mono<BoardStudyDTO> update(BoardStudyDTO boardStudyDTO) {
        return Mono.just(boardStudyDTO)
                .filter(dto -> !StringUtil.isNullOrEmpty(dto.getOid()))
                .flatMap(dto ->
                        Mono.just(dto)
                                .map(data -> new BoardStudyEntity(data))
                )
                .flatMap(entity ->
                        update(entity)
                                .map(savedEntity -> new BoardStudyDTO(savedEntity))
                );
    }

    private Mono<BoardStudyEntity> update(BoardStudyEntity boardStudyEntity) {
        return Mono.just(boardStudyEntity)
                .map(entity -> {
                    Optional.ofNullable(entity.getTitle()).ifPresent(title -> boardStudyEntity.setTitle(title));
                    Optional.ofNullable(entity.getContents()).ifPresent(contents -> boardStudyEntity.setContents(contents));
                    Optional.ofNullable(entity.getHashTags()).ifPresent(tags -> boardStudyEntity.setHashTags(tags));
                    Optional.ofNullable(entity.isRecruiting()).ifPresent(isRecruiting -> boardStudyEntity.setRecruiting(isRecruiting));
                    return boardStudyEntity;
                })
                .flatMap(savedEntity -> boardStudyMongoRepository.save(savedEntity))
                .switchIfEmpty(Mono.just(new BoardStudyEntity()));
    }

    @Override
    public Flux<BoardStudyDTO> delete(String oid) {
        return boardStudyMongoRepository.deleteById(oid)
                .thenMany(list());
    }

    @Override
    public Flux<BoardStudyDTO> list() {
        return boardStudyMongoRepository.findAll()
                .flatMap(boardStudyEntity ->
                        Mono.just(boardStudyEntity)
                                .map(entity -> new BoardStudyDTO(entity))
                );
    }
}
