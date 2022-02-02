package com.btaka.domain.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String oid;
    @NonNull
    private String userId;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String email;
    @NonNull
    private String mobile;

    private String oauthId;
}
