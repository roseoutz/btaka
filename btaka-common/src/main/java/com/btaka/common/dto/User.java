package com.btaka.common.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String oid;
    @NonNull
    private String userId;
    @NonNull
    private String username;
    @NonNull
    private String email;
    @NonNull
    private String mobile;

    private String oauthId;
}
