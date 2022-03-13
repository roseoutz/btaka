package com.btaka.domain.web.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;

    private String password;

    private boolean isOauth;

    private String oauthId;

    private String token;

}
