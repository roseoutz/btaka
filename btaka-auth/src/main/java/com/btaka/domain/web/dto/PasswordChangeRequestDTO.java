package com.btaka.domain.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String oid;

    private String password;

    private String originalPassword;

    private String passwordCheck;

}
