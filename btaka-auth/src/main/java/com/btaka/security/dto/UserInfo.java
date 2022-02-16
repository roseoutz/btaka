package com.btaka.security.dto;

import com.btaka.board.common.constants.Roles;
import com.btaka.domain.entity.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
public class UserInfo implements UserDetails {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final String oid;
    private final String email;
    private final String password;
    private final String username;
    private final String mobile;
    private final Roles roles;

    public UserInfo() {
        this.oid = null;
        this.password = null;
        this.username = null;
        this.email = null;
        this.mobile = null;
        this.roles = Roles.ROLE_GUEST;
    }

    public UserInfo(UserEntity userEntity) {
        this.oid = userEntity.getOid();
        this.password = userEntity.getPassword();
        this.username = userEntity.getUsername();
        this.email = userEntity.getEmail();
        this.mobile = userEntity.getMobile();
        this.roles = userEntity.getRoles();
    }

    public static UserInfo toUserInfo(UserEntity user) {
        return user == null ? new UserInfo() : new UserInfo(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Set.<GrantedAuthority>of(new SimpleGrantedAuthority(this.roles.name()));
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
