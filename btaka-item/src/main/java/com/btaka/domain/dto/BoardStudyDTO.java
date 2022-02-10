package com.btaka.domain.dto;

import com.btaka.domain.entity.BoardStudyEntity;
import com.btaka.domain.entity.BoardStudyReplyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
public class BoardStudyDTO {

    public BoardStudyDTO(BoardStudyEntity entity) {
        this.oid = entity.getOid();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.hashTags.addAll(entity.getHashTags());
        this.isRecruiting = entity.isRecruiting();
        this.likes = entity.getLikes();
        this.insertUser = entity.getInsertUser();
        this.insertTime = entity.getInsertTime();
        this.updateTime = entity.getUpdateTime();
        entity.getBoardStudyReplyEntity()
                .forEach(replyEntity -> this.boardStudyReplyDTOS.add(new BoardStudyReplyDTO(replyEntity)));
    }

    private String oid;
    private String title;
    private String contents;
    private List<String> hashTags = new ArrayList<>();
    private boolean isRecruiting;
    private int likes;
    private String insertUser;
    private LocalDateTime insertTime;
    private LocalDateTime updateTime;
    private List<BoardStudyReplyDTO> boardStudyReplyDTOS = new ArrayList<>();
}
