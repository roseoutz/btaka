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
public class SignUpResponseDTO implements Serializable {

    private String oid;
    private String email;
    private String userName;
}
