package com.btaka.domain.dto;

import com.btaka.domain.entity.BoardStudyReplyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
public class BoardStudyReplyDTO {

    public BoardStudyReplyDTO(BoardStudyReplyEntity entity) {
        this.oid = entity.getOid();
        this.parentOid = entity.getParentOid();
        this.reply = entity.getReply();
        this.insertUser = entity.getInsertUser();
        this.insertTime = entity.getInsertTime();
        this.updateTime = entity.getUpdateTime();
    }

    private String oid;
    private String parentOid;
    private String reply ;
    private String insertUser;
    private LocalDateTime insertTime;
    private LocalDateTime updateTime;
}
