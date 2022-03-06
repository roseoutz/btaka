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
@Entity(name = "btaka_board_dev_free")
public class BoardDevFreeEntity extends AbstractJPAEntity {

    @Id
    private String oid;

    private String title;

    private String contents;

    private String hashTags;

    private String insertUser;
}
