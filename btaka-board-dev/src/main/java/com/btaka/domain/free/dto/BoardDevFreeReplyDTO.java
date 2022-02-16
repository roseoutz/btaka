package com.btaka.domain.free.dto;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDevFreeReplyDTO {

    private String oid;
    private String postOid;
    private String parentOid;
    private String reply ;
    private int likes;
    private String insertUser;
    private LocalDateTime insertTime;
    private LocalDateTime updateTime;
}
