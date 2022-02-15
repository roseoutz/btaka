package com.btaka.domain.service.impl;

import com.btaka.domain.dto.BoardStudyDTO;
import com.btaka.domain.dto.BoardStudyReplyDTO;
import com.btaka.domain.entity.BoardStudyEntity;
import com.btaka.domain.entity.BoardStudyReplyEntity;
import com.btaka.domain.repo.BoardStudyMongoRepository;
import com.btaka.domain.service.BoardStudyService;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RequiredArgsConstructor
@Service("defaultBoardStudyService")
public class DefaultBoardStudyService implements BoardStudyService {

    private final BoardStudyMongoRepository boardStudyMongoRepository;

    @Override
    public Mono<BoardStudyDTO> get(String oid) {
        return getByOid(oid)
                .flatMap(boardStudyEntity -> {
                    boardStudyEntity.setViews(boardStudyEntity.getViews() + 1);
                    return update(boardStudyEntity);
                })
                .flatMap(boardStudyEntity ->
                        Mono.just(boardStudyEntity)
                                .map(BoardStudyDTO::new)
                ).switchIfEmpty(Mono.just(new BoardStudyDTO()));
    }

    private Mono<BoardStudyEntity> getByOid(String oid) {
        return boardStudyMongoRepository.findById(oid)
                .switchIfEmpty(Mono.just(new BoardStudyEntity()));
    }

    @Override
    public Mono<BoardStudyDTO> add(BoardStudyDTO boardStudyDTO) {
        return Mono.just(new BoardStudyEntity(boardStudyDTO))
                .flatMap(boardStudyMongoRepository::save)
                .flatMap(entity ->
                        Mono.just(entity)
                                .map(BoardStudyDTO::new)
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
    public Mono<BoardStudyDTO> update(BoardStudyDTO boardStudyDTO) {
        return Mono.just(boardStudyDTO)
                .filter(dto -> !StringUtil.isNullOrEmpty(dto.getOid()))
                .flatMap(dto ->
                        Mono.just(dto)
                                .map(BoardStudyEntity::new)
                )
                .flatMap(entity ->
                        update(entity)
                                .map(BoardStudyDTO::new)
                );
    }

    private Mono<BoardStudyEntity> update(BoardStudyEntity boardStudyEntity) {
        return Mono.just(boardStudyEntity)
                .map(entity -> {
                    Optional.of(entity.getTitle()).ifPresent(boardStudyEntity::setTitle);
                    Optional.of(entity.getContents()).ifPresent(boardStudyEntity::setContents);
                    Optional.ofNullable(entity.getHashTags()).ifPresent(boardStudyEntity::setHashTags);
                    Optional.of(entity.isRecruiting()).ifPresent(boardStudyEntity::setRecruiting);
                    return boardStudyEntity;
                })
                .flatMap(boardStudyMongoRepository::save)
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
                                .map(BoardStudyDTO::new)
                );
    }
}
