package com.btaka.domain.free.dto;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDevFreeDTO {

    private String oid;
    private String title;
    private String contents;
    private List<String> hashTags = new ArrayList<>();
    private String insertUser;
    private LocalDateTime insertTime;
    private LocalDateTime updateTime;
}
