package com.btaka.config.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfigDTO implements Serializable {

    private String oid;

    private String key;

    private String group;

    private String value;

    private String description;

    private LocalDateTime insertTime;

    private LocalDateTime updateTime;

}
