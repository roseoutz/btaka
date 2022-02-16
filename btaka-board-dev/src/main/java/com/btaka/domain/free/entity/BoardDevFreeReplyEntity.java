package com.btaka.domain.free.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("btaka_board_dev_free_reply")
public class BoardDevFreeReplyEntity {

    @Id
    private String oid;

    @Indexed
    private String postOid;

    @Indexed
    private String parentOid;

    private String reply ;

    private int likes;

    private String insertUser;

    @CreatedDate
    private LocalDateTime insertTime;

    @LastModifiedDate
    private LocalDateTime updateTime;
}
