package com.btaka.data.user.entity;

import com.btaka.data.user.dto.UserInfoDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "btaka_user_info")
public class UserInfoEntity {

    public UserInfoEntity() {
    }

    public UserInfoEntity(UserInfoDTO userInfoDTO) {
        this.oid = userInfoDTO.getOid();
        this.userId = userInfoDTO.getUserId();
        this.username = userInfoDTO.getUsername();
    }

    @Id
    private String oid;

    private String userId;

    private String username;
}
