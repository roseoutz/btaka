package com.btaka.dto;

import com.btaka.data.user.dto.UserDTO;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseDTO {

    public ResponseDTO(UserDTO userDTO) {
        this.oid = userDTO.getOid();
        this.userId = userDTO.getUserId();
        this.username = userDTO.getUsername();
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
