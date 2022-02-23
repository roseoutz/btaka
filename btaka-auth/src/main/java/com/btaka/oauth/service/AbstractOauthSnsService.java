package com.btaka.oauth.service;

import com.btaka.board.common.dto.SnsUser;
import com.btaka.domain.service.UserOauthService;
import com.btaka.domain.service.UserService;
import com.btaka.domain.service.dto.UserOauthDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class AbstractOauthSnsService implements OauthSnsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserService userService;

    private final UserOauthService userOauthService;

    protected final String clientId;

    protected final String clientSecret;

    protected final String authUrl;

    protected final String tokenUrl;

    protected final String userInfoUrl;

    protected final String redirectUri;

    private final GsonJsonParser gsonJsonParser = new GsonJsonParser();

    protected Map<String, Object> convertObjectMap(Map<String, String> stringStringMap) {
        if (Objects.isNull(stringStringMap)) {
            return null;
        }

        return stringStringMap
                .entrySet()
                .stream()
                .collect(Collectors.toConcurrentMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }
    protected String getTokenParamMap(String code, String state) {
        return getTokenParamMap(code, state, "authorization_code");
    }

    protected String getTokenParamMap(String code, String state, String grantType) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("grant_type",Objects.isNull(grantType) ? "authorization_code": grantType);
        paramMap.put("client_id", getClientId());
        paramMap.put("client_secret", getClientSecret());
        paramMap.put("code", code);
        paramMap.put("state", state);
        // paramMap.put("redirect_uri", getRedirectUri());
        String paramStr = null;
        StringBuilder postData = new StringBuilder();
        for(Map.Entry<String,String> param : paramMap.entrySet()) {
            if(postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8));
            postData.append('=');
            postData.append(URLEncoder.encode(param.getValue(), StandardCharsets.UTF_8));
        }

        paramStr = postData.toString();


        return paramStr;

    }

    @Override
    public String getClientId() {
        return clientId;
    }

    protected String getClientSecret() {
        return clientSecret;
    }

    protected Mono<String> getToken(String code, String state) {
        return getWebClient(tokenUrl)
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(getTokenParamMap(code, state))
                .retrieve()
                .bodyToMono(String.class);
    }

    protected Mono<Map> getUserInfo(Map<String, Object> tokenInfoMap) {
        return getWebClient(userInfoUrl)
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", tokenInfoMap.get("token_type") + " " + tokenInfoMap.get("access_token"))
                .retrieve()
                .bodyToMono(Map.class);
    }



    protected String getRedirectUri() {
        return redirectUri + getSite();
    }

    protected WebClient getWebClient(String url) {
        return WebClient.create(url);
    }

    protected abstract SnsUser convertUserInfo(String token, Map<String, Object> userInfoMap);

    protected Mono<SnsUser> getUserInfo(String code, String state) {
        return getToken(code, state)
                .flatMap(token -> {
                    logger.debug(getSite() + " from get Token == " + token);
                    Map<String, Object> tokenInfoMap = gsonJsonParser.parseMap(token);
                    return getUserInfo(tokenInfoMap)
                            .map(map -> convertUserInfo(token, map));
                });

    }

    @Override
    public Mono<SnsUser> auth(String code, String state, String nonce) {
        return getUserInfo(code, state)
                .flatMap(snsUser ->
                    userOauthService.get(getSite(), snsUser.getId())
                            .filter(Objects::nonNull)
                            .then(Mono.just(snsUser))
                );
    }

    @Override
    public Mono<SnsUser> userInfo(String authCode, String state, String nonce) {
        return getUserInfo(authCode, state);
    }

    @Override
    public Mono<SnsUser> register(String userOid, String code, String state, String nonce) {
        return userInfo(code, state, nonce)
                .flatMap(snsUser ->
                        userOauthService.get(this.getSite(), snsUser.getId())
                            .filter(userOauthDTO -> !Objects.isNull(userOauthDTO.getOauthId()))
                            .map(userOauthDTO -> snsUser)
                            .switchIfEmpty(
                                    userService.findByOid(userOid)
                                            .flatMap(user -> userOauthService.save(UserOauthDTO.builder()
                                                    .userOid(user.getOid())
                                                    .oauthId(snsUser.getId())
                                                    .oauthSite(getSite())
                                                    .email(snsUser.getEmail())
                                                    .build()))
                                            .then(Mono.just(snsUser))
                            )
                );
    }
}
