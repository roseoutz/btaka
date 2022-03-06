package com.btaka.domain.study.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

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
