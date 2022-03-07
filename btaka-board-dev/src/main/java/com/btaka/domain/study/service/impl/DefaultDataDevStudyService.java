package com.btaka.domain.study.service.impl;

import com.btaka.board.common.page.DefaultPageResult;
import com.btaka.common.exception.BtakaException;
import com.btaka.common.service.AbstractDataService;
import com.btaka.constant.BoardErrorCode;
import com.btaka.domain.study.dto.BoardDevStudyDTO;
import com.btaka.domain.study.entity.BoardDevStudyEntity;
import com.btaka.domain.study.repo.BoardDevStudyJPARepository;
import com.btaka.domain.study.service.BoardDevStudyService;
import com.btaka.dto.BoardListResponseDTO;
import com.btaka.dto.BoardResponseDTO;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service("defaultBoardDevStudyService")
public class DefaultDataDevStudyService extends AbstractDataService<BoardDevStudyEntity, BoardDevStudyDTO> implements BoardDevStudyService {

    @Autowired
    private BoardDevStudyJPARepository boardDevStudyJPARepository;

    public DefaultDataDevStudyService() {
        super(BoardDevStudyEntity.class, BoardDevStudyDTO.class);
    }



    @Override
    protected BoardDevStudyEntity toEntity(BoardDevStudyDTO dto) {
        BoardDevStudyEntity entity = modelMapper.map(dto, BoardDevStudyEntity.class);
        return entity;
    }

    @Override
    protected BoardDevStudyDTO toDto(BoardDevStudyEntity entity) {
        BoardDevStudyDTO dto = modelMapper.map(entity, BoardDevStudyDTO.class);
        return dto;
    }

    @Override
    public Mono<BoardDevStudyDTO> getDto(String oid) {
        return getByOid(oid)
                .filter(boardDevStudyEntity -> !Objects.isNull(boardDevStudyEntity.getOid()))
                .switchIfEmpty(Mono.error(new BtakaException(BoardErrorCode.POST_NOT_FOUND)))
                .flatMap(boardDevStudyEntity -> {
                    boardDevStudyEntity.setViews(boardDevStudyEntity.getViews() + 1);
                    return update(boardDevStudyEntity)
                            .map(this::toDto);
                })
                .switchIfEmpty(Mono.error(new BtakaException(BoardErrorCode.POST_NOT_FOUND)));
    }

    @Override
    public Mono<BoardResponseDTO> get(String oid) {
        return getDto(oid)
                .flatMap(dto ->
                        Mono.just(dto)
                                .map(boardDevStudyDTO -> BoardResponseDTO.of(boardDevStudyDTO, null))
                ).switchIfEmpty(Mono.error(new BtakaException(BoardErrorCode.POST_NOT_FOUND)));
    }

    private Mono<BoardDevStudyEntity> getByOid(String oid) {
        return Mono.just(oid)
                .publishOn(Schedulers.boundedElastic())
                .map(postOid -> boardDevStudyJPARepository.findById(postOid))
                .map(optionalEntity -> optionalEntity.orElseThrow(() -> new BtakaException(BoardErrorCode.POST_NOT_FOUND)));
    }

    @Override
    public Mono<BoardResponseDTO> add(BoardDevStudyDTO boardDevStudyDTO) {
        return Mono.just(toEntity(boardDevStudyDTO))
                .publishOn(Schedulers.boundedElastic())
                .map(entity -> {
                    if (Objects.isNull(entity.getOid())) {
                        entity.setOid(getUUID());
                    }
                    BoardDevStudyEntity savedEntity = boardDevStudyJPARepository.save(entity);
                    return BoardResponseDTO.of(toDto(savedEntity), null);
                });
    }

    @Override
    @Transactional
    public Mono<BoardResponseDTO> update(BoardDevStudyDTO boardDevStudyDTO) {
        return Mono.just(boardDevStudyDTO)
                .filter(dto -> !StringUtil.isNullOrEmpty(dto.getOid()))
                .flatMap(dto -> {
                    BoardDevStudyEntity entity = toEntity(dto);
                    return update(entity)
                            .map(updatedEntity -> BoardResponseDTO.of(toDto(updatedEntity), null));
                });
    }

    private Mono<BoardDevStudyEntity> update(BoardDevStudyEntity boardDevStudyEntity) {
        return Mono.just(boardDevStudyEntity)
                .map(entity -> {
                    Optional.of(entity.getTitle()).ifPresent(boardDevStudyEntity::setTitle);
                    Optional.of(entity.getContents()).ifPresent(boardDevStudyEntity::setContents);
                    Optional.ofNullable(entity.getHashTags()).ifPresent(boardDevStudyEntity::setHashTags);
                    Optional.of(entity.isRecruiting()).ifPresent(boardDevStudyEntity::setRecruiting);
                    return boardDevStudyEntity;
                })
                .publishOn(Schedulers.boundedElastic())
                .map(entity -> boardDevStudyJPARepository.save(entity))
                .switchIfEmpty(Mono.error(new BtakaException(BoardErrorCode.POST_NOT_FOUND)));
    }

    @Override
    public Mono<Void> delete(String oid) {
        return Mono.just(oid)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(postOid -> {
                    boardDevStudyJPARepository.deleteById(postOid);
                    return Mono.empty();
                });
    }

    @Override
    public Mono<BoardListResponseDTO> list(Pageable pageable) {
        return Mono.just(boardDevStudyJPARepository.findAll(pageable))
                .publishOn(Schedulers.boundedElastic())
                .map(pageResult -> {
                    List<BoardDevStudyDTO> boardDevStudyDTOS = new CopyOnWriteArrayList<>();
                    pageResult.get().forEach(entity -> boardDevStudyDTOS.add(toDto(entity)));
                    return BoardListResponseDTO.of(DefaultPageResult.of(boardDevStudyDTOS));
                });
    }
}
