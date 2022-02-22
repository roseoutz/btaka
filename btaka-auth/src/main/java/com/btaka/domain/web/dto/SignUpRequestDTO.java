package com.btaka.domain.web.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class SignUpRequestDTO implements Serializable {

    @NonNull
    @NotEmpty(message = "{btaka.sign.up.email.empty}")
    private String email;

    @NonNull
    @NotEmpty(message = "{btaka.sign.up.password.empty}")
    private String password;

    @NonNull
    @NotEmpty(message = "{btaka.sign.up.username.empty}")
    private String userName;

    @NonNull
    @NotEmpty(message = "{btaka.sign.up.mobile.empty}")
    private String mobile;
    private String birthdate;
    private String gender;
    private String address;
    private String addressDetail;
    private String postNum;
    private String role;
}
