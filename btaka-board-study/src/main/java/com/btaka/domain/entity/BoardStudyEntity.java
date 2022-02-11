package com.btaka.domain.entity;

import com.btaka.domain.dto.BoardStudyDTO;
import com.btaka.domain.dto.BoardStudyReplyDTO;
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

    public BoardStudyEntity(BoardStudyDTO dto) {
        this.oid = dto.getOid();
        this.title = dto.getTitle();
        this.contents = dto.getContents();
        this.hashTags.addAll(dto.getHashTags());
        this.isRecruiting = dto.isRecruiting();
        this.likes = dto.getLikes();
        this.insertUser = dto.getInsertUser();
        this.insertTime = dto.getInsertTime();
        this.updateTime = dto.getUpdateTime();

        if (dto.getBoardStudyReplyDTOS() != null)
            dto.getBoardStudyReplyDTOS()
                .forEach(replyDTO -> this.boardStudyReplyEntity.add(new BoardStudyReplyEntity(replyDTO)));
        else this.boardStudyReplyEntity = new ArrayList<>();

        this.replyCount = this.boardStudyReplyEntity.size();
    }

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

    private int replyCount;

}
