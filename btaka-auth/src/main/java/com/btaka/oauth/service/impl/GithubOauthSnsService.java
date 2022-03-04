package com.btaka.oauth.service.impl;

import com.btaka.board.common.dto.SnsUser;
import com.btaka.common.exception.BtakaException;
import com.btaka.config.OauthConfig;
import com.btaka.domain.service.UserOauthService;
import com.btaka.domain.service.UserService;
import com.btaka.oauth.service.AbstractOauthSnsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class GithubOauthSnsService extends AbstractOauthSnsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public GithubOauthSnsService(UserService userService, UserOauthService userOauthService, String clientId, String clientSecret, String authUrl, String tokenUrl, String userInfoUrl, String redirectUri) {
        super(userService, userOauthService, clientId, clientSecret, authUrl, tokenUrl, userInfoUrl, redirectUri);
    }

    public GithubOauthSnsService(UserService userService, UserOauthService userOauthService, String redirectUrl, OauthConfig.SocialInfo socialInfo) {
        super(userService, userOauthService, socialInfo.getClientId(), socialInfo.getClientSecret(), socialInfo.getAuthUrl(), socialInfo.getTokenUrl(), socialInfo.getUserInfoUrl(), redirectUrl);
    }

    @Override
    public String getAuthUrl(String state, String nonce) {
        String gitAuthUrl = authUrl +"?response_type=code&client_id=" + clientId + "&state=" + state + "&scope=user" +
                "&redirect_uri=" + URLEncoder.encode(redirectUri + "/" + getSite(), StandardCharsets.UTF_8);

        logger.debug("[BTAKA] Github Auth Url = " + gitAuthUrl);
        return gitAuthUrl;
    }


    @Override
    public String getSite() {
        return "github";
    }

    protected String getTokenParamMap(String code, String state, String grantType) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("client_id", getClientId());
        paramMap.put("client_secret", getClientSecret());
        paramMap.put("code", code);
        paramMap.put("redirect_uri", getRedirectUri());
        return getTokenParamStr(paramMap);
    }

    @Override
    protected SnsUser convertUserInfo(String token, Map<String, Object> userInfoMap) {

        String id = userInfoMap.get("id")+"";
        String email = Objects.isNull(userInfoMap.get("email")) ? (String) userInfoMap.get("login") : (String) userInfoMap.get("email");
        return SnsUser.builder()
                .token(token)
                .id(id)
                .email(email)
                .infoMap(userInfoMap).build();
    }

    @Override
    protected Map<String, Object> convertTokenToMap(String token) {
        Map<String, Object> tokenParamMap = new ConcurrentHashMap<>();

        if (!Objects.isNull(token)) {
            String[] payloads = token.split("&");
            Arrays.stream(payloads).forEach(payload -> {
                String[] param = payload.split("=");
                tokenParamMap.put(param[0], param[1]);
            });
        }
        return tokenParamMap;
    }

    @Override
    protected Mono<Map> getUserInfo(Map<String, Object> tokenInfoMap) {
        return getWebClient(userInfoUrl)
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", tokenInfoMap.get("token_type") + " " + tokenInfoMap.get("access_token"))
                .retrieve()
                .bodyToMono(Map.class)
                .doOnError(BtakaException::new);
    }
}
