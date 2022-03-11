package com.btaka.oauth.service.impl;

import com.btaka.board.common.dto.SnsUser;
import com.btaka.common.exception.BtakaException;
import com.btaka.config.OauthConfig;
import com.btaka.constant.Social;
import com.btaka.domain.service.UserOauthService;
import com.btaka.domain.service.UserService;
import com.btaka.oauth.service.AbstractOauthSnsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.GsonJsonParser;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class GoogleOauthSnsService extends AbstractOauthSnsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GsonJsonParser gsonJsonParser = new GsonJsonParser();


    public GoogleOauthSnsService(UserService userService, UserOauthService userOauthService, String clientId, String clientSecret, String authUrl, String tokenUrl, String userInfoUrl, String redirectUri) {
        super(userService, userOauthService, clientId, clientSecret, authUrl, tokenUrl, userInfoUrl, redirectUri);
    }

    public GoogleOauthSnsService(UserService userService, UserOauthService userOauthService, String redirectUrl, OauthConfig.SocialInfo socialInfo) {
        super(userService, userOauthService, socialInfo.getClientId(), socialInfo.getClientSecret(), socialInfo.getAuthUrl(), socialInfo.getTokenUrl(), socialInfo.getUserInfoUrl(), redirectUrl);
    }

    @Override
    public String getAuthUrl(String state, String nonce) {
        String googleAuthUrl = authUrl +"?response_type=code&client_id=" + clientId + "&state=" + state +
                "&redirect_uri=" + URLEncoder.encode(redirectUri + "/" + getSite(), StandardCharsets.UTF_8) + "&scope=" + URLEncoder.encode("openid email profile", StandardCharsets.UTF_8);

        logger.debug("[BTAKA] Google Auth Url = " + googleAuthUrl);
        return googleAuthUrl;
    }

    @Override
    protected Mono<Map> getUserInfo(Map<String, Object> tokenInfoMap) {
        return getWebClient(userInfoUrl)
                .get()
                .header("Authorization", tokenInfoMap.get("token_type") + " " + tokenInfoMap.get("access_token"))
                .retrieve()
                .bodyToMono(Map.class)
                .doOnNext(respone -> logger.debug("[BTAKA Oauth Token Response]" + respone))
                .doOnError(BtakaException::new);
    }


    @Override
    public String getSite() {
        return Social.GOOGLE.getName();
    }

    @Override
    protected SnsUser convertUserInfo(String token, Map<String, Object> userInfoMap) {

        String id = userInfoMap.get("id") +"";
        String email = userInfoMap.get("email") +"";


        return SnsUser.builder()
                .token(token)
                .id(id)
                .email(email)
                .infoMap(userInfoMap)
                .build();
    }

    @Override
    protected Map<String, Object> convertTokenToMap(String token) {
        return gsonJsonParser.parseMap(token);
    }
}
