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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.List;

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
                .flatMap(entity -> Mono.just(entity)
                        .zipWith(replyMongoRepository.findByParentOid(entity.getOid()).collectList(),
                                (board, reply) -> {
                                    board.setBoardStudyReplyEntity(reply);
                                    return board;
                                })
                )
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
    public Mono<BoardStudyDTO> addReply(BoardStudyReplyDTO boardStudyReplyDTO) {
        return boardStudyMongoRepository.findById(boardStudyReplyDTO.getParentOid())
                .flatMap(boardEntity -> {
                    List<BoardStudyReplyEntity> replyEntities = boardEntity.getBoardStudyReplyEntity();
                    BoardStudyReplyEntity replyEntity = new BoardStudyReplyEntity(boardStudyReplyDTO);
                    if (replyEntities == null) {
                        replyEntities = Arrays.asList(replyEntity);
                    } else {
                        replyEntities.add(replyEntity);
                    }
                    boardEntity.setBoardStudyReplyEntity(replyEntities);
                   return update(boardEntity);
                })
                .flatMap(entity ->
                        Mono.just(entity)
                            .map(savedEntity -> new BoardStudyDTO(savedEntity))
                );
    }

    @Override
    public Mono<BoardStudyDTO> update(BoardStudyDTO boardStudyDTO) {
        return Mono.just(boardStudyDTO)
                .filter(dto -> StringUtil.isNullOrEmpty(dto.getOid()))
                .flatMap(dto ->
                        Mono.just(dto)
                                .map(data -> new BoardStudyEntity(data))
                )
                .flatMap(entity ->
                        update(entity)
                                .map(savedEntity -> new BoardStudyDTO(savedEntity))
                );
    }

    public Mono<BoardStudyEntity> update(BoardStudyEntity boardStudyEntity) {
        return Mono.just(boardStudyEntity)
                .flatMap(entity ->
                        getByOid(entity.getOid())
                                .filter(savedEntity -> savedEntity != null)
                                .doOnNext(savedEntity -> update(entity, savedEntity))
                ).switchIfEmpty(Mono.just(new BoardStudyEntity()));
    }

    private BoardStudyEntity update(BoardStudyEntity entity, BoardStudyEntity savedEntity) {
        if (!StringUtil.isNullOrEmpty(entity.getTitle())) savedEntity.setTitle(entity.getTitle());
        if (!StringUtil.isNullOrEmpty(entity.getContents())) savedEntity.setContents(entity.getContents());
        if (!entity.getHashTags().isEmpty()) savedEntity.setHashTags(entity.getHashTags());
        if (entity.isRecruiting() != savedEntity.isRecruiting()) savedEntity.setRecruiting(entity.isRecruiting());
        return savedEntity;
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
