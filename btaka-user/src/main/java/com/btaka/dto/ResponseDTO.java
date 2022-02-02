package com.btaka.dto;

import com.btaka.data.user.entity.UserInfoEntity;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseDTO {

    public ResponseDTO(UserInfoEntity userInfoEntity) {
        this.oid = userInfoEntity.getOid();
        this.userId = userInfoEntity.getUserId();
        this.username = userInfoEntity.getUsername();
        this.result = true;
    }

    private String oid;

    private String userId;

    private String password;

    private String username;

    private boolean result;

    private String errorCode;

    private String errorMsg;


}
