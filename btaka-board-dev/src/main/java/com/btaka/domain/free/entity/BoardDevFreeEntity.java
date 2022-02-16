package com.btaka.domain.free.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("btaka_board_dev_free")
public class BoardDevFreeEntity {

    @Id
    private String oid;

    private String title;

    private String contents;

    private List<String> hashTags = new ArrayList<>();

    private String insertUser;

    @CreatedDate
    private LocalDateTime insertTime;

    @LastModifiedDate
    private LocalDateTime updateTime;
}
