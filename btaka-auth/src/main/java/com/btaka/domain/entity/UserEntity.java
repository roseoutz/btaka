package com.btaka.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document("btaka_user")
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

    private String oauthId;
}
