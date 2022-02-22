package com.btaka.domain.service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOauthDTO {

    private String oid;

    private String userOid;

    private String oauthSite;

    private String oauthId;

    private String email;

    private LocalDateTime loginAt;

    private LocalDateTime expiredAt;

}
