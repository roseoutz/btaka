package com.btaka.oauth.service.impl;

import com.btaka.board.common.dto.SnsUser;
import com.btaka.config.OauthConfig;
import com.btaka.domain.service.UserOauthService;
import com.btaka.domain.service.UserService;
import com.btaka.oauth.service.AbstractOauthSnsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.GsonJsonParser;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class KakaoOauthSnsService extends AbstractOauthSnsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GsonJsonParser gsonJsonParser = new GsonJsonParser();

    public KakaoOauthSnsService(UserService userService, UserOauthService userOauthService, String clientId, String clientSecret, String authUrl, String tokenUrl, String userInfoUrl, String redirectUri) {
        super(userService, userOauthService, clientId, clientSecret, authUrl, tokenUrl, userInfoUrl, redirectUri);
    }

    public KakaoOauthSnsService(UserService userService, UserOauthService userOauthService, String redirectUrl, OauthConfig.SocialInfo socialInfo) {
        super(userService, userOauthService, socialInfo.getClientId(), socialInfo.getClientSecret(), socialInfo.getAuthUrl(), socialInfo.getTokenUrl(), socialInfo.getUserInfoUrl(), redirectUrl);
    }

    @Override
    public String getAuthUrl(String state, String nonce) {
        String kakaoAuthUrl = authUrl +"?response_type=code&client_id=" + clientId + "&state=" + state +
                "&redirect_uri=" + URLEncoder.encode(redirectUri + "/" + getSite(), StandardCharsets.UTF_8);

        logger.debug("[BTAKA] Kakao Auth Url = " + kakaoAuthUrl);
        return kakaoAuthUrl;
    }


    @Override
    public String getSite() {
        return "kakao";
    }

    @Override
    protected SnsUser convertUserInfo(String token, Map<String, Object> userInfoMap) {

        String id = userInfoMap.get("id")+"";

        Map<String, String> kakaoAccountMap = ((Map<String,String>)userInfoMap.get("kakao_account"));
        String email = kakaoAccountMap.get("email");
        return SnsUser.builder()
                .token(token)
                .id(id)
                .email(email)
                .infoMap(convertObjectMap(kakaoAccountMap)).build();
    }

    @Override
    protected Map<String, Object> convertTokenToMap(String token) {
        return gsonJsonParser.parseMap(token);
    }
}
