package com.btaka.jwt.impl;

import com.btaka.common.dto.User;
import com.btaka.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("defaultJwtService")
public class DefaultJwtService implements JwtService {

    @Value("${btaka.value.jwt.secret}")
    private String secret;

    @Value("${btaka.value.jwt.tokenMaxValidTime}")
    private String tokenMaxValidTime;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    @Override
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public boolean isExpired(String token) {
        final Date expiredTime = getExpiredTime(token);
        return expiredTime.before(new Date());
    }

    @Override
    public Date getExpiredTime(String token) {
        return getClaims(token).getExpiration();
    }

    @Override
    public String generateToken(User user) {

        long expirationTimeLong = Long.parseLong(tokenMaxValidTime);
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setHeader(Collections.singletonMap(Header.TYPE, Header.JWT_TYPE))
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .claim("userinfo", getUserClaim(user))
                .signWith(key)
                .compact();
    }

    private Map<String, String> getUserClaim(User user) {
        Map<String, String> claimMap = new ConcurrentHashMap<>();

        claimMap.put("username", user.getUsername());
        claimMap.put("email", user.getEmail());
        claimMap.put("mobile", user.getMobile());
        claimMap.put("role", user.getRoles().name());

        return claimMap;
    }

    @Override
    public boolean isValidToken(String token) {
        return !isExpired(token);
    }
}
