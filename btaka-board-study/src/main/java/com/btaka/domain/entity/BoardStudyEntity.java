package com.btaka.domain.entity;

import com.btaka.domain.dto.BoardStudyDTO;
import com.btaka.domain.dto.BoardStudyReplyDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document("btaka_board_study")
public class BoardStudyEntity {

    @PersistenceConstructor
    public BoardStudyEntity(BoardStudyDTO dto) {
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

        if (dto.getBoardStudyReplyDTOS() != null)
            dto.getBoardStudyReplyDTOS()
                .forEach(replyDTO -> this.boardStudyReplyEntity.add(new BoardStudyReplyEntity(replyDTO)));
        else this.boardStudyReplyEntity = new ArrayList<>();

        this.replyCount = this.boardStudyReplyEntity.size();
    }

    @Id
    private String oid;

    @NonNull
    private String title;

    @NonNull
    private String contents;

    private List<String> hashTags = new ArrayList<>();

    private boolean isRecruiting;

    private int likes;

    private int views;

    private String insertUser;

    @CreatedDate
    private LocalDateTime insertTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

    private List<BoardStudyReplyEntity> boardStudyReplyEntity;

    private int replyCount;

}
