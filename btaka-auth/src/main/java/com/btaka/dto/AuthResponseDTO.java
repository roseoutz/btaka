package com.btaka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accessToken;

    private boolean result;

    private int statusCode;

}
