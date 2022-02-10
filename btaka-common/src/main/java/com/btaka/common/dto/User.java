package com.btaka.common.dto;

import com.btaka.common.constant.Roles;
import lombok.*;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String oid;
    private String userId;
    private String username;
    private String email;
    private String mobile;
    private String oauthId;
    private Roles roles;

}
