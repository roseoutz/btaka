package com.btaka.data.user.dto;

import com.btaka.data.user.entity.UserEntity;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDTO {

    public UserDTO(UserEntity entity) {
        this.oid = entity.getOid();
        this.userId = entity.getUserId();
        this.password = entity.getPassword();
        this.username = entity.getUsername();
    }

    private String oid;

    private String userId;

    private String password;

    private String username;
}
