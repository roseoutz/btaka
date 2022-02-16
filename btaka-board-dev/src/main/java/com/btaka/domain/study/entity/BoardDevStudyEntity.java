package com.btaka.domain.study.entity;

import com.btaka.domain.study.dto.BoardDevStudyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("btaka_board_dev_study")
public class BoardDevStudyEntity {

    public BoardDevStudyEntity(BoardDevStudyDTO dto) {
        this.oid = dto.getOid();
        this.title = dto.getTitle();
        this.contents = dto.getContents();
        this.hashTags.addAll(dto.getHashTags());
        this.isRecruiting = dto.isRecruiting();
        this.likes = dto.getLikes();
        this.views = 0;
        this.insertUser = dto.getInsertUser();
        this.insertTime = dto.getInsertTime() == null ? LocalDateTime.now() : dto.getInsertTime();
        this.updateTime = dto.getUpdateTime() == null ? this.insertTime : dto.getUpdateTime();
        /*
        this.boardStudyReplyEntity = new ArrayList<>();

        if (dto.getBoardStudyReplyDTOS() != null)
            dto.getBoardStudyReplyDTOS()
                .forEach(replyDTO -> this.boardStudyReplyEntity.add(new BoardStudyReplyEntity(replyDTO)));
        else this.boardStudyReplyEntity = new ArrayList<>();

        this.replyCount = this.boardStudyReplyEntity != null ? this.boardStudyReplyEntity.size() : 0;
         */
    }

    @Id
    private String oid;

    @NonNull
    private String title;

    @NonNull
    private String contents;

    private String repository;

    private String workspace;

    private String contact;

    private List<String> hashTags = new ArrayList<>();

    private boolean isRecruiting;

    private int likes;

    private int views;

    private String insertUser;

    @CreatedDate
    private LocalDateTime insertTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

    /*
    private List<BoardStudyReplyEntity> boardStudyReplyEntity;

    private int replyCount;

    public void addReply(BoardStudyReplyEntity boardStudyReplyEntity) {
        if (this.boardStudyReplyEntity == null) {
            this.boardStudyReplyEntity = new ArrayList<>();
        }

        this.boardStudyReplyEntity.add(boardStudyReplyEntity);
    }
    */
}
