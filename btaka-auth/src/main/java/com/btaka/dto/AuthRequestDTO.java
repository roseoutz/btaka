package com.btaka.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "{btaka.auth.email.empty}")
    private String email;
    @NotEmpty(message = "{btaka.auth.password.empty}")
    private String password;
}
