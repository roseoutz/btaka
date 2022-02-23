package com.btaka.domain.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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

    @NonNull
    @Indexed
    private String userOid;

    private String oauthSite;

    @NonNull
    @Indexed
    private String oauthId;

    @NonNull
    private String email;

    private LocalDateTime loginAt;

    private LocalDateTime expiredAt;

}
