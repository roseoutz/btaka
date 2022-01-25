package com.btaka.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@RequiredArgsConstructor
@ConstructorBinding
@Configuration
@ConfigurationProperties("btaka.services")
public class ServicesConfig {

    private String authService;


}
