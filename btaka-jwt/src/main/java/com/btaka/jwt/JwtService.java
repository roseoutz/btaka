package com.btaka.jwt;

import com.btaka.common.dto.User;
import io.jsonwebtoken.Claims;

import java.util.Date;

public interface JwtService {

    Claims getClaims(String token);

    String getUsername(String token);

    boolean isExpired(String token);

    Date getExpiredTime(String token);

    String generateToken(User user);

    boolean isValidToken(String token);
}
