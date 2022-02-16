package com.btaka.domain.entity;

import com.btaka.board.common.constants.Roles;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("btaka_user")
public class UserEntity {

    @Id
    private String oid;
    @NonNull
    private String email;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String mobile;

    private String birthdate;
    private String gender;
    private String address;
    private String addressDetail;
    private String postNum;

    private Roles roles;

}
