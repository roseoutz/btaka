package com.btaka.cache.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthCacheDTO {

    private String sid;

    private String accessToken;

    private AuthInfo authInfo;

}
