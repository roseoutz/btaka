package com.btaka.domain.entity;

import com.btaka.common.constant.Roles;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document("user")
public class UserEntity {

    @Id
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

    private Roles roles;

    private String oauthId;
}
