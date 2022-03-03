package com.btaka.domain.study.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("btaka_board_dev_study_reply")
public class BoardDevStudyReplyEntity {

    @Id
    private String oid;

    @Indexed
    @DBRef
    private BoardDevStudyEntity post;

    @Indexed
    @DBRef
    private BoardDevStudyReplyEntity parent;

    private String reply ;

    private int likes;

    private String insertUser;

    @CreatedDate
    private LocalDateTime insertTime;

    @LastModifiedDate
    private LocalDateTime updateTime;
}
