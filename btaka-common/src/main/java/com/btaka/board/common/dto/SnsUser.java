package com.btaka.board.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SnsUser {

    private String token;

    private String id;

    private String name;

    private String email;

    private String phone;

    private Map<String, Object> infoMap;


}
