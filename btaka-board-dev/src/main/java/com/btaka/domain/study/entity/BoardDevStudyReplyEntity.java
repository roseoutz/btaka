package com.btaka.domain.study.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "btaka_board_dev_study_reply")
public class BoardDevStudyReplyEntity extends AbstractJPAEntity {

    @Id
    private String oid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_oid")
    private BoardDevStudyEntity post;

    private String postTargetOid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_oid")
    private BoardDevStudyReplyEntity parent;

    private String parentTargetOid;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<BoardDevStudyReplyEntity> children = new ArrayList<>();

    private String reply ;

    private int likes;

}
