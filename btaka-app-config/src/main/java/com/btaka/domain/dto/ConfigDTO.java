package com.btaka.domain.dto;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfigDTO {

    private String oid;

    private String key;

    private String group;

    private String value;

    private String description;

    private LocalDateTime insertTime;

    private LocalDateTime updateTime;

}
