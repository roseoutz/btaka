package com.btaka.config;

import com.btaka.domain.service.UserOauthService;
import com.btaka.domain.service.UserService;
import com.btaka.oauth.factory.SnsServiceFactory;
import com.btaka.oauth.service.impl.KakaoOauthSnsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "btaka")
public class OauthConfig {

    private final UserService userService;
    private final UserOauthService userOauthService;

    @Value("${btaka.oauth.redirect.url:http://localhost:14000}")
    private String redirectUrl;

    @Data
    public static class SocialInfo {
        private String clientId;
        private String clientSecret;
        private String authUrl;
        private String tokenUrl;
        private String userInfoUrl;
    }

    private Map<String, OauthConfig.SocialInfo> social;

    public void setSocial(Map<String, OauthConfig.SocialInfo> social) {
        this.social = social;
    }

    @Bean
    public SnsServiceFactory snsServiceFactory() {
        SnsServiceFactory snsServiceFactory = new SnsServiceFactory();
        return snsServiceFactory
                .add(new KakaoOauthSnsService(userService, userOauthService, redirectUrl , social.get("kakao")));
    }



}
