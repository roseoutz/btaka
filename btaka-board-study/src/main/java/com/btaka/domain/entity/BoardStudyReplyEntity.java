package com.btaka.domain.entity;

import com.btaka.domain.dto.BoardStudyReplyDTO;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Data
@Document("btaka_board_study_reply")
public class BoardStudyReplyEntity {

    //@PersistenceConstructor
    public BoardStudyReplyEntity(BoardStudyReplyDTO dto) {
        this.oid = dto.getOid();
        this.parentOid = dto.getParentOid();
        // this.boardStudyEntity = new BoardStudyEntity(dto.getBoardStudyDTO());
        this.reply = dto.getReply();
        this.insertUser = dto.getInsertUser();
        this.insertTime = dto.getInsertTime() == null ? LocalDateTime.now() : dto.getInsertTime();
        this.updateTime = dto.getUpdateTime() == null ? this.insertTime : dto.getUpdateTime();
    }

    @Id
    private String oid;

    private String parentOid;

    private BoardStudyEntity boardStudyEntity;

    private String reply ;


    private String insertUser;

    @CreatedDate
    private LocalDateTime insertTime;

    @LastModifiedDate
    private LocalDateTime updateTime;
}
