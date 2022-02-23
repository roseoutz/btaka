package com.btaka.jwt.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class JwtDTO {

    private String oid;
    private String userId;
    private LocalDateTime loginAt;
    private LocalDateTime expiredAt;
    private String accessToken;

}
