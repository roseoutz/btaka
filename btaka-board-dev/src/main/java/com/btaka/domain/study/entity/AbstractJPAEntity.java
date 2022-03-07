package com.btaka.domain.study.entity;

import lombok.Getter;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class AbstractJPAEntity {

    private String insertUser;

    private LocalDateTime insertTime;

    private LocalDateTime updateTime;

    @PrePersist
    private void prePersist() {
        this.insertTime = LocalDateTime.now();
        this.updateTime = insertTime;
    }

    @PreUpdate
    private void preUpdate() {
        this.updateTime = LocalDateTime.now();
    }

}
