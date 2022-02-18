package com.btaka.cache.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthInfo {

    private String userId;

    private String accessToken;

    private LocalDateTime loginAt;

    private LocalDateTime expiredAt;

}
