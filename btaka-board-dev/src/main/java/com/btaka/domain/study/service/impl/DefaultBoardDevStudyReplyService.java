package com.btaka.domain.study.service.impl;

import com.btaka.domain.service.AbstractBoardService;
import com.btaka.domain.study.dto.BoardDevStudyDTO;
import com.btaka.domain.study.dto.BoardDevStudyReplyDTO;
import com.btaka.domain.study.entity.BoardDevStudyReplyEntity;
import com.btaka.domain.study.repo.BoardDevStudyReplyMongoRepository;
import com.btaka.domain.study.service.BoardDevStudyReplyService;
import com.btaka.domain.study.service.BoardDevStudyService;
import com.netflix.discovery.converters.Auto;
import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service("defaultBoardDevStudyReplyService")
public class DefaultBoardDevStudyReplyService extends AbstractBoardService<BoardDevStudyReplyEntity, BoardDevStudyReplyDTO> implements BoardDevStudyReplyService {

    @Autowired
    protected BoardDevStudyReplyMongoRepository replyMongoRepository;

    @Resource(name = "${btaka.bean.board.dev.study.service:defaultBoardDevStudyService}")
    private BoardDevStudyService boardDevStudyService;

    public DefaultBoardDevStudyReplyService() {
        super(BoardDevStudyReplyEntity.class, BoardDevStudyReplyDTO.class);
    }

    @Override
    public Mono<List<BoardDevStudyReplyDTO>> get(@NonNull String postOid) {
        return Mono.just(postOid)
                .filter(oid -> !Objects.isNull(oid))
                .flatMap(oid -> replyMongoRepository.findAllByPostOid(oid)
                        .map(BoardDevStudyReplyDTO::new)
                        .collectList()
                )
                .switchIfEmpty(Mono.just(List.of(new BoardDevStudyReplyDTO())));
    }

    @Override
    public Mono<List<BoardDevStudyReplyDTO>> add(BoardDevStudyReplyDTO boardDevStudyReplyDTO) {
        return Mono.just(new BoardDevStudyReplyEntity(boardDevStudyReplyDTO))
                .filter(entity -> entity.getParentOid() != null && entity.getPostOid() != null)
                .flatMap(replyMongoRepository::save)
                .flatMap(savedEntity -> get(boardDevStudyReplyDTO.getPostOid()));
    }

    @Override
    public Mono<BoardDevStudyDTO> update(BoardDevStudyReplyDTO dto) {
        return replyMongoRepository.findByOidAndParentOid(dto.getOid(), dto.getParentOid())
                .flatMap(savedEntity -> update(dto, savedEntity)
                        .flatMap(replyEntity -> boardDevStudyService.get(replyEntity.getOid()))
                );
    }

    private Mono<BoardDevStudyReplyEntity> update(BoardDevStudyReplyDTO dto, BoardDevStudyReplyEntity savedEntity) {
        if (!StringUtil.isNullOrEmpty(dto.getReply())) savedEntity.setReply(dto.getReply());

        return Mono.just(savedEntity);
    }

    @Override
    public Mono<BoardDevStudyDTO> delete(BoardDevStudyReplyDTO dto) {
        return replyMongoRepository.deleteById(dto.getOid())
                .then(boardDevStudyService.get(dto.getParentOid()));
    }

}
