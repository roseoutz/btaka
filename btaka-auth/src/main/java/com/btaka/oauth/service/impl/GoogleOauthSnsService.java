package com.btaka.oauth.service.impl;

import com.btaka.board.common.dto.SnsUser;
import com.btaka.common.exception.BtakaException;
import com.btaka.config.OauthConfig;
import com.btaka.domain.service.UserOauthService;
import com.btaka.domain.service.UserService;
import com.btaka.jwt.JwtService;
import com.btaka.oauth.service.AbstractOauthSnsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.GsonJsonParser;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    protected String getTokenParamMap(String code, String state, String grantType) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("grant_type", Objects.isNull(grantType) ? "authorization_code": grantType);
        paramMap.put("client_id", getClientId());
        paramMap.put("client_secret", getClientSecret());
        paramMap.put("code", code);
        if (!Objects.isNull(state)) paramMap.put("state", state);
        paramMap.put("redirect_uri", getRedirectUri());
        return getTokenParamStr(paramMap);
    }

    @Override
    public String getSite() {
        return "google";
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
