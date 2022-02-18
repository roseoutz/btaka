package com.btaka.cache.entity;

import com.btaka.cache.dto.AuthInfo;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("auth_cache")
public class AuthCacheEntity implements Serializable {

    @Id
    private String oid;

    private AuthInfo authInfo;


}
