package com.btaka.jwt;

import com.btaka.board.common.dto.User;
import com.btaka.jwt.dto.JwtDTO;
import io.jsonwebtoken.Claims;

import java.util.Date;

public interface JwtService {

    Claims getClaims(String token);

    String getUsername(String token);

    boolean isExpired(String token);

    Date getExpiredTime(String token);

    JwtDTO generateToken(User user);

    boolean isValidToken(String token);
}
