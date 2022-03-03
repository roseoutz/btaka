package com.btaka.config;

import com.btaka.domain.service.UserOauthService;
import com.btaka.domain.service.UserService;
import com.btaka.oauth.factory.SnsServiceFactory;
import com.btaka.oauth.service.impl.FacebookOauthSnsService;
import com.btaka.oauth.service.impl.GithubOauthSnsService;
import com.btaka.oauth.service.impl.GoogleOauthSnsService;
import com.btaka.oauth.service.impl.KakaoOauthSnsService;
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

        snsServiceFactory
                .add(new KakaoOauthSnsService(userService, userOauthService, redirectUrl , social.get("kakao")))
                .add(new GithubOauthSnsService(userService, userOauthService, redirectUrl, social.get("github")))
                .add(new GoogleOauthSnsService(userService, userOauthService, redirectUrl, social.get("google")))
                .add(new FacebookOauthSnsService(userService, userOauthService, redirectUrl, social.get("facebook")))
        ;


        return snsServiceFactory;
    }



}
