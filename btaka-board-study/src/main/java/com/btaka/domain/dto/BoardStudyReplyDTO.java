package com.btaka.domain.dto;

import com.btaka.domain.entity.BoardStudyReplyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardStudyReplyDTO {

    public BoardStudyReplyDTO(BoardStudyReplyEntity entity) {
        this.oid = entity.getOid();
        this.parentOid = entity.getParentOid();
        // this.boardStudyDTO = new BoardStudyDTO(entity.getBoardStudyEntity());
        this.reply = entity.getReply();
        this.insertUser = entity.getInsertUser();
        this.insertTime = entity.getInsertTime();
        this.updateTime = entity.getUpdateTime();
    }

    private String oid;
    private String parentOid;
    private BoardStudyDTO boardStudyDTO;
    private String reply ;
    private String insertUser;
    private LocalDateTime insertTime;
    private LocalDateTime updateTime;
}
