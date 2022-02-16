package com.btaka.domain.study.service.impl;

import com.btaka.domain.study.dto.BoardDevStudyDTO;
import com.btaka.domain.study.entity.BoardDevStudyEntity;
import com.btaka.domain.study.repo.BoardDevStudyMongoRepository;
import com.btaka.domain.study.service.BoardDevStudyService;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service("defaultBoardDevStudyService")
public class DefaultBoardDevStudyService implements BoardDevStudyService {

    private final BoardDevStudyMongoRepository boardDevStudyMongoRepository;

    @Override
    public Mono<BoardDevStudyDTO> get(String oid) {
        return getByOid(oid)
                .doOnNext(boardDevStudyEntity -> {
                    if (Objects.isNull(boardDevStudyEntity.getOid())) {
                        throw new NullPointerException("This Post is not exists!, Post Oid = " + oid);
                    }
                })
                .filter(boardDevStudyEntity -> !Objects.isNull(boardDevStudyEntity.getOid()))
                .flatMap(boardDevStudyEntity -> {
                    boardDevStudyEntity.setViews(boardDevStudyEntity.getViews() + 1);
                    return update(boardDevStudyEntity);
                })
                .flatMap(boardDevStudyEntity ->
                        Mono.just(boardDevStudyEntity)
                                .map(BoardDevStudyDTO::new)
                ).switchIfEmpty(Mono.just(new BoardDevStudyDTO()));
    }

    private Mono<BoardDevStudyEntity> getByOid(String oid) {
        return boardDevStudyMongoRepository.findById(oid)
                .doOnNext(boardDevStudyEntity -> log.info(boardDevStudyEntity.getOid()))
                .switchIfEmpty(Mono.just(new BoardDevStudyEntity()));
    }

    @Override
    public Mono<BoardDevStudyDTO> add(BoardDevStudyDTO boardDevStudyDTO) {
        return Mono.just(new BoardDevStudyEntity(boardDevStudyDTO))
                .flatMap(boardDevStudyMongoRepository::save)
                .flatMap(entity ->
                        Mono.just(entity)
                                .map(BoardDevStudyDTO::new)
                );
    }

    /*
    @Override
    @Transactional
    public Mono<BoardStudyDTO> addReply(BoardStudyReplyDTO boardStudyReplyDTO) {
        return Mono.just(boardStudyReplyDTO)
                .flatMap(dto ->
                        getByOid(boardStudyReplyDTO.getParentOid())
                                .filter(Objects::nonNull)
                                .flatMap(boardStudyEntity -> {
                                    boardStudyEntity.addReply(new BoardStudyReplyEntity(boardStudyReplyDTO));
                                    return boardStudyMongoRepository.save(boardStudyEntity);
                                })
                .flatMap(entity ->
                    Mono.just(entity)
                            .map(BoardStudyDTO::new)
                ));
    }
     */

    @Override
    @Transactional
    public Mono<BoardDevStudyDTO> update(BoardDevStudyDTO boardDevStudyDTO) {
        return Mono.just(boardDevStudyDTO)
                .filter(dto -> !StringUtil.isNullOrEmpty(dto.getOid()))
                .flatMap(dto ->
                        Mono.just(dto)
                                .map(BoardDevStudyEntity::new)
                )
                .flatMap(entity ->
                        update(entity)
                                .map(BoardDevStudyDTO::new)
                );
    }

    private Mono<BoardDevStudyEntity> update(BoardDevStudyEntity boardDevStudyEntity) {
        return Mono.just(boardDevStudyEntity)
                .map(entity -> {
                    Optional.ofNullable(entity.getTitle()).ifPresent(boardDevStudyEntity::setTitle);
                    Optional.ofNullable(entity.getContents()).ifPresent(boardDevStudyEntity::setContents);
                    Optional.ofNullable(entity.getHashTags()).ifPresent(boardDevStudyEntity::setHashTags);
                    Optional.ofNullable(entity.isRecruiting()).ifPresent(boardDevStudyEntity::setRecruiting);
                    return boardDevStudyEntity;
                })
                .flatMap(boardDevStudyMongoRepository::save)
                .switchIfEmpty(Mono.just(new BoardDevStudyEntity()));
    }

    @Override
    public Flux<BoardDevStudyDTO> delete(String oid) {
        return boardDevStudyMongoRepository.deleteById(oid)
                .thenMany(list());
    }

    @Override
    public Flux<BoardDevStudyDTO> list() {
        return boardDevStudyMongoRepository.findAll()
                .flatMap(boardDevStudyEntity ->
                        Mono.just(boardDevStudyEntity)
                                .map(BoardDevStudyDTO::new)
                );
    }
}
