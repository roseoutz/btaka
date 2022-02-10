package com.btaka.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String password;
}
