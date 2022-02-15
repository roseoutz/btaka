package com.btaka.domain.entity;

import com.btaka.domain.dto.BoardStudyReplyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("btaka_board_study_reply")
public class BoardStudyReplyEntity {

    public BoardStudyReplyEntity(BoardStudyReplyDTO dto) {
        this.oid = dto.getOid();
        this.postOid = dto.getPostOid();
        this.parentOid = dto.getParentOid();
        this.reply = dto.getReply();
        this.likes = dto.getLikes();
        this.insertUser = dto.getInsertUser();
        this.insertTime = dto.getInsertTime() == null ? LocalDateTime.now() : dto.getInsertTime();
        this.updateTime = dto.getUpdateTime() == null ? this.insertTime : dto.getUpdateTime();
    }

    @Id
    private String oid;

    // @DBRef(db = "btaka_board_study_reply")
    private String postOid;

    // @DBRef(db = "btaka_board_study")
    private String parentOid;

    private String reply ;

    private int likes;

    private String insertUser;

    @CreatedDate
    private LocalDateTime insertTime;

    @LastModifiedDate
    private LocalDateTime updateTime;
}
