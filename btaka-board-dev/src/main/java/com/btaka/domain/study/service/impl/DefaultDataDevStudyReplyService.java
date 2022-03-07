package com.btaka.domain.study.service.impl;

import com.btaka.common.exception.BtakaException;
import com.btaka.common.service.AbstractDataService;
import com.btaka.constant.BoardErrorCode;
import com.btaka.domain.study.dto.BoardDevStudyDTO;
import com.btaka.domain.study.dto.BoardDevStudyReplyDTO;
import com.btaka.domain.study.entity.BoardDevStudyEntity;
import com.btaka.domain.study.entity.BoardDevStudyReplyEntity;
import com.btaka.domain.study.repo.BoardDevStudyJPARepository;
import com.btaka.domain.study.repo.BoardDevStudyReplyJPARepository;
import com.btaka.domain.study.service.BoardDevStudyReplyService;
import com.btaka.domain.study.service.BoardDevStudyService;
import io.netty.util.internal.StringUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service("defaultBoardDevStudyReplyService")
public class DefaultDataDevStudyReplyService extends AbstractDataService<BoardDevStudyReplyEntity, BoardDevStudyReplyDTO> implements BoardDevStudyReplyService {

    @Autowired
    protected BoardDevStudyReplyJPARepository replyJPARepository;

    @Autowired
    protected BoardDevStudyJPARepository boardDevStudyJPARepository;



    @Resource(name = "${btaka.bean.board.dev.study.service:defaultBoardDevStudyService}")
    private BoardDevStudyService boardDevStudyService;

    public DefaultDataDevStudyReplyService() {
        super(BoardDevStudyReplyEntity.class, BoardDevStudyReplyDTO.class);
    }

    @Override
    protected BoardDevStudyReplyEntity toEntity(BoardDevStudyReplyDTO dto) {
        BoardDevStudyReplyEntity entity = modelMapper.map(dto, BoardDevStudyReplyEntity.class);
        return entity;
    }

    @Override
    protected BoardDevStudyReplyDTO toDto(BoardDevStudyReplyEntity entity) {
        BoardDevStudyReplyDTO replyDTO = BoardDevStudyReplyDTO.builder()
                .oid(entity.getOid())
                .reply(entity.getReply())
                .likes(entity.getLikes())
                .insertTime(entity.getInsertTime())
                .insertUser(entity.getInsertUser())
                .updateTime(entity.getUpdateTime())
                .build();
        return replyDTO;
    }

    @Override
    public Mono<List<BoardDevStudyReplyDTO>> get(@NonNull String postOid) {
        return Mono.just(postOid)
                .filter(oid -> !Objects.isNull(oid))
                .flatMap(oid -> Mono.just(replyJPARepository.findWithByInsertUserAndParentByPostOidAscNullsFirstReplyInsertTimeAsc(oid))
                        .publishOn(Schedulers.boundedElastic())
                        .map(replyEntities -> {
                            List<BoardDevStudyReplyDTO> replyDTOS = new CopyOnWriteArrayList<>();
                            for (BoardDevStudyReplyEntity entity : replyEntities) {
                                replyDTOS.add(toDto(entity));
                            }
                            return replyDTOS;
                        })
                )
                .switchIfEmpty(Mono.just(List.of(new BoardDevStudyReplyDTO())));
    }

    @Override
    public Mono<BoardDevStudyReplyDTO> getOne(String oid, String postOid) {
        return null;
    }

    @Override
    public Mono<List<BoardDevStudyReplyDTO>> add(BoardDevStudyReplyDTO boardDevStudyReplyDTO) {
        return Mono.just(toEntity(boardDevStudyReplyDTO))
                .publishOn(Schedulers.boundedElastic())
                .flatMap(entity -> {
                    if (Objects.isNull(entity.getPostTargetOid())) {
                        return Mono.error(new BtakaException(BoardErrorCode.POST_NOT_FOUND));
                    }

                    Optional<BoardDevStudyEntity> optionalBoardDevStudyEntity = boardDevStudyJPARepository.findById(entity.getPostTargetOid());
                    if (!optionalBoardDevStudyEntity.isPresent()) {
                        return Mono.error(new BtakaException(BoardErrorCode.POST_NOT_FOUND));
                    }
                    entity.setPost(optionalBoardDevStudyEntity.get());

                    if (!Objects.isNull(entity.getParentTargetOid())) {
                        replyJPARepository.findById(entity.getParentTargetOid()).ifPresent(reply -> entity.setParent(reply));
                    }
                    entity.setOid(getUUID());
                    return Mono.just(replyJPARepository.save(entity))
                            .flatMap(savedEntity -> get(savedEntity.getPostTargetOid()));
                });
    }

    @Override
    public Mono<BoardDevStudyDTO> update(BoardDevStudyReplyDTO dto) {
        return null;
        /*return Mono.just(dto)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(replyDto -> {
                    Optional<BoardDevStudyReplyEntity> optionalEntity = replyMongoRepository.findById(replyDto.getOid());
                    return optionalEntity.map(boardDevStudyReplyEntity -> update(replyDto, boardDevStudyReplyEntity)
                            .flatMap(savedDto -> boardDevStudyService.get(savedDto.getOid())))
                            .orElseGet(() -> Mono.error(new BtakaException(BoardErrorCode.POST_NOT_FOUND)));
                });*/
    }

    private Mono<BoardDevStudyReplyEntity> update(BoardDevStudyReplyDTO dto, BoardDevStudyReplyEntity savedEntity) {
        if (!StringUtil.isNullOrEmpty(dto.getReply())) savedEntity.setReply(dto.getReply());

        return Mono.just(savedEntity);
    }

    @Override
    public Mono<BoardDevStudyDTO> delete(BoardDevStudyReplyDTO dto) {
        return null;
        /*return Mono.just(dto)
                .publishOn(Schedulers.boundedElastic())
                .map(replyDto -> {
                    replyMongoRepository.deleteById(dto.getOid());
                    return replyDto;
                })
                .then(boardDevStudyService.get(dto.getParent().getOid()));*/
    }

}
