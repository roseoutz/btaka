package com.btaka.domain.study.dto;

import com.btaka.domain.study.entity.BoardDevStudyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDevStudyDTO {

    public BoardDevStudyDTO(BoardDevStudyEntity entity) {
        this.oid = entity.getOid();
        this.title = entity.getTitle();
        this.contents = entity.getContents();
        this.repository = entity.getRepository();
        this.workspace = entity.getWorkspace();
        this.contact = entity.getContact();
        this.hashTags = entity.getHashTags();
        this.isRecruiting = entity.isRecruiting();
        this.likes = entity.getLikes();
        this.views = entity.getViews();
        this.insertUser = entity.getInsertUser();
        this.insertTime = entity.getInsertTime();
        this.updateTime = entity.getUpdateTime();
        /*
        this.boardStudyReplyDTOS = new ArrayList<>();

        if (entity.getBoardStudyReplyEntity() != null) {
            entity.getBoardStudyReplyEntity()
                    .forEach(replyEntity -> this.boardStudyReplyDTOS.add(new BoardStudyReplyDTO(replyEntity)));
        }

        if (this.boardStudyReplyDTOS!= null && !this.boardStudyReplyDTOS.isEmpty()) this.replyCount = this.boardStudyReplyDTOS.size();
         */
    }

    private String oid;
    private String title;
    private String contents;
    private String repository;
    private String workspace;
    private String contact;
    private List<String> hashTags;
    private boolean isRecruiting;
    private int likes;
    private int views;
    private String insertUser;
    private LocalDateTime insertTime;
    private LocalDateTime updateTime;
    /*
    private List<BoardStudyReplyDTO> boardStudyReplyDTOS;
    private int replyCount = 0;
     */
}
