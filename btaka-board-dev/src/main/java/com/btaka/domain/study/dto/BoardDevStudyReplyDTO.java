package com.btaka.domain.study.dto;

import com.btaka.domain.study.entity.BoardDevStudyReplyEntity;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDevStudyReplyDTO {

    private String oid;
    private BoardDevStudyDTO post;
    private String postOid;
    private BoardDevStudyReplyDTO parent;
    private String parentOid;
    private String reply ;
    private int likes;
    private String insertUser;
    private LocalDateTime insertTime;
    private LocalDateTime updateTime;
}
