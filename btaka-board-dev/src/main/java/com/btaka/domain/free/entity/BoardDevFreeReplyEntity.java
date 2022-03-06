package com.btaka.domain.free.entity;

import com.btaka.domain.study.entity.AbstractJPAEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "btaka_board_dev_free_reply")
public class BoardDevFreeReplyEntity extends AbstractJPAEntity {

    @Id
    private String oid;

    private String postOid;

    private String parentOid;

    private String reply ;

    private int likes;

    private String insertUser;
}
