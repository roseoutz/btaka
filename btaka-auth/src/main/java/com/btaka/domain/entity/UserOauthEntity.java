package com.btaka.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("btaka_user_oauth")
public class UserOauthEntity {

    @Id
    private String oid;

    private String oauthSite;

    @NonNull
    private String oauthId;

    @NonNull
    private String email;

    private LocalDateTime loginAt;

    private LocalDateTime expiredAt;

}
