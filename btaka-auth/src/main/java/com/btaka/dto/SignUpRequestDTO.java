package com.btaka.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDTO implements Serializable {

    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String userName;
    @NonNull
    private String mobile;
    private String birthdate;
    private String gender;
    private String address;
    private String addressDetail;
    private String postNum;
    private String role;
}
