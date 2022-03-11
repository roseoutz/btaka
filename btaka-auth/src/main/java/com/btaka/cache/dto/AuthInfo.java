package com.btaka.cache.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthInfo {

    private String userId;

    private String accessToken;

    private LocalDateTime loginAt;

    private LocalDateTime expiredAt;

    private LocalDateTime maxValidTokenTime;

}
