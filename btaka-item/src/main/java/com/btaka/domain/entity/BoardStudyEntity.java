package com.btaka.domain.entity;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("btaka_board_study")
public class BoardStudyEntity {

    @Id
    private String oid;

    @NonNull
    private String title;

    @NonNull
    private String contents;

    private List<String> hashTags = new ArrayList<>();

    private boolean isRecruiting;

    private int likes;

    private String insertUser;

    @CreatedDate
    private LocalDateTime insertTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

    @ReadOnlyProperty
    @DocumentReference(lookup = "{'targetOid':?#{#self._id}}")
    private List<BoardStudyReplyEntity> boardStudyReplyEntity;

}
