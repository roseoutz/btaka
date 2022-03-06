package com.btaka.domain.study.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "btaka_board_dev_study")
public class BoardDevStudyEntity extends AbstractJPAEntity {

    @Id
    private String oid;

    @NonNull
    private String title;

    @NonNull
    private String contents;

    private String repository;

    private String workspace;

    private String contact;

    private String hashTags;

    private boolean isRecruiting;

    private int likes;

    private int views;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "oid")
    private List<BoardDevStudyReplyEntity> reply = new ArrayList<>();

}
