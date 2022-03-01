package com.btaka.domain.study.dto;

import com.btaka.domain.study.entity.BoardDevStudyEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDevStudyDTO {

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
