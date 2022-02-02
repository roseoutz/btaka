package com.btaka.data.user.dto;

import com.btaka.data.user.entity.UserInfoEntity;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoDTO {


    public UserInfoDTO(UserInfoEntity userInfoEntity) {
        this.oid = userInfoEntity.getOid();
        this.userId = userInfoEntity.getUserId();
        this.username = userInfoEntity.getUsername();
    }

    private String oid;

    private String userId;

    private String username;
}
