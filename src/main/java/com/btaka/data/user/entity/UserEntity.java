package com.btaka.data.user.entity;

import com.btaka.data.user.dto.UserDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user")
public class UserEntity {

    @Id
    private String oid;

    private String userId;

    private String password;

    private String username;
}
