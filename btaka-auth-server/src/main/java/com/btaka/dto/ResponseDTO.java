package com.btaka.dto;

import com.btaka.data.user.entity.UserEntity;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseDTO {

    public ResponseDTO(UserEntity userEntity) {
        this.oid = userEntity.getOid();
        this.userId = userEntity.getUserId();
        this.username = userEntity.getUsername();
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
