package com.btaka.board.common.dto;

import com.btaka.board.common.constants.Roles;
import lombok.*;

import java.beans.Transient;
import java.time.LocalDateTime;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String oid;
    private String email;
    private String password;
    private int failCount;
    private boolean isLocked;
    private LocalDateTime lockedTime;
    private String username;
    private String mobile;
    private String birthdate;
    private String gender;
    private String address;
    private String addressDetail;
    private String postNum;
    private Roles roles;

}
