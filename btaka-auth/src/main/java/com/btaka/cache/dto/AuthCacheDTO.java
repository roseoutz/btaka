package com.btaka.cache.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthCacheDTO {

    private String sid;

    private AuthInfo authInfo;

}
