package com.btaka.security.dto;

import com.btaka.domain.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserInfo implements UserDetails {

    private final String oid;
    private final String userId;
    private final String password;
    private final String username;
    private final String oauthId;

    public UserInfo() {
        this.oid = null;
        this.userId = null;
        this.password = null;
        this.username = null;
        this.oauthId = null;
    }

    public UserInfo(UserEntity userEntity) {
        this.oid = userEntity.getOid();
        this.userId = userEntity.getUserId();
        this.password = userEntity.getPassword();
        this.username = userEntity.getUsername();
        this.oauthId = userEntity.getOauthId();
    }

    public static UserInfo toUserInfo(UserEntity user) {
        return user == null ? new UserInfo() : new UserInfo(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
