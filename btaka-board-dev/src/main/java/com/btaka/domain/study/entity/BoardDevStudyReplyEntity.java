package com.btaka.domain.study.entity;

import com.btaka.domain.study.dto.BoardDevStudyReplyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("btaka_board_dev_study_reply")
public class BoardDevStudyReplyEntity {

    public BoardDevStudyReplyEntity(BoardDevStudyReplyDTO dto) {
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

    @Indexed
    private String postOid;

    @Indexed
    private String parentOid;

    private String reply ;

    private int likes;

    private String insertUser;

    @CreatedDate
    private LocalDateTime insertTime;

    @LastModifiedDate
    private LocalDateTime updateTime;
}
