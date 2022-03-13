package com.btaka.config;

import com.btaka.config.impl.DefaultConfigClientService;
import com.btaka.jwt.JwtService;
import com.btaka.jwt.impl.DefaultJwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnClass(RedisConfig.class)
@Configuration
public class CommonBeanConfig {

    /*
    * todo Redis에서 Config를 가져온 이후 처리한다.
     */
    @Value("${btaka.value.jwt.secret}")
    private String secret;

    @Value("${btaka.value.jwt.tokenMaxValidTime}")
    private String tokenMaxValidTime;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ConfigClientService configClientService() {
        String configServer = "BTAKA-CONFIG-SERVICE";
        return new DefaultConfigClientService(configServer + "/config", configServer + "/configs");
    }

    @Bean(name = "defaultJwtService")
    public JwtService jwtService() {
        return new DefaultJwtService(secret, tokenMaxValidTime);
    }
}
