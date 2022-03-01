package com.btaka.domain.study.entity;

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
