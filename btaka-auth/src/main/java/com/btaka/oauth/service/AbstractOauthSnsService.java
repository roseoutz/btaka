package com.btaka.oauth.service;

import com.btaka.board.common.dto.SnsUser;
import com.btaka.common.exception.BtakaException;
import com.btaka.constant.AuthParamConst;
import com.btaka.domain.service.UserOauthService;
import com.btaka.domain.service.UserService;
import com.btaka.domain.service.dto.UserOauthDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    protected String getTokenParam(String code, String state, String redirectUri) {
        return getTokenParam(code, state, redirectUri, "authorization_code");
    }

    protected String getTokenParam(String code, String state, String redirectUri, String grantType) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put(AuthParamConst.PARAM_OAUTH_GRANT_TYPE.getKey(), Objects.isNull(grantType) ? "authorization_code": grantType);
        paramMap.put(AuthParamConst.PARAM_OAUTH_CLIENT_ID.getKey(), getClientId());
        paramMap.put(AuthParamConst.PARAM_OAUTH_CLIENT_SECRET.getKey(), getClientSecret());
        paramMap.put(AuthParamConst.PARAM_OAUTH_AUTHORIZATION_CODE.getKey(), code);
        if (!Objects.isNull(redirectUri)) paramMap.put(AuthParamConst.PARAM_OAUTH_REDIRECT_URI.getKey(), redirectUri);
        if (!Objects.isNull(state)) paramMap.put(AuthParamConst.PARAM_OAUTH_STATE.getKey(), state);
        // paramMap.put("redirect_uri", getRedirectUri());
        return getTokenParamStr(paramMap);
    }

    protected String getTokenParamStr(Map<String, String> paramMap) {
        String paramStr = null;
        StringBuilder postData = new StringBuilder();
        for(Map.Entry<String,String> param : paramMap.entrySet()) {
            if(postData.length() != 0) postData.append('&');
            if (!Objects.isNull(param.getKey())  && !Objects.isNull(param.getValue())) {
                postData.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8));
                postData.append('=');
                postData.append(URLEncoder.encode(param.getValue(), StandardCharsets.UTF_8));
            }
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

    protected Mono<String> getToken(String code, String state, String redirectUri) {
        String getTokenParam = getTokenParam(code, state, redirectUri);
        return getAccessToken(code, state, getTokenParam);
    }

    protected Mono<String> getAccessToken(String code, String state, String paramStr) {
        return getWebClient(tokenUrl)
                .post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(paramStr)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(respone -> logger.debug("[BTAKA Oauth Token Response]" + respone))
                .doOnError(BtakaException::new);
    }

    protected Mono<Map> getUserInfo(Map<String, Object> tokenInfoMap) {
        return getWebClient(userInfoUrl)
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .header(
                        AuthParamConst.PARAM_OAUTH_HEADER_TOKEN_KEY.getKey(),
                        tokenInfoMap.get(AuthParamConst.PARAM_OAUTH_TOKEN_TYPE.getKey()) + " " + tokenInfoMap.get(AuthParamConst.PARAM_OAUTH_ACCESS_TOKEN.getKey())
                )
                .retrieve()
                .bodyToMono(Map.class)
                .doOnNext(respone -> logger.debug("[BTAKA Oauth Token Response]" + respone))
                .doOnError(BtakaException::new);
    }



    protected String getRedirectUri() {
        return redirectUri + "/" + getSite();
    }

    protected String getAuthRedirectUrl() {
        return redirectUri + "/" + getSite();
    }

    protected String getRegisterRedirectUrl() {
        return redirectUri + "/register/" + getSite();
    }

    protected WebClient getWebClient(String url) {
        return WebClient.create(url);
    }

    protected abstract SnsUser convertUserInfo(String token, Map<String, Object> userInfoMap);

    protected abstract Map<String, Object> convertTokenToMap(String token);


    protected Mono<SnsUser> getUserInfo(String accessToken) {
        return Mono.just(accessToken)
                .flatMap(token -> {
                    logger.debug(getSite() + " from get Token == " + token);
                    Map<String, Object> tokenInfoMap = convertTokenToMap(token);
                    return getUserInfo(tokenInfoMap)
                            .map(map -> convertUserInfo(token, map));
                })
                .doOnError(BtakaException::new);
    }

    @Override
    public Mono<SnsUser> auth(String code, String state, String nonce) {
        return getToken(code, state, getAuthRedirectUrl())
                .flatMap(this::getUserInfo)
                .doOnError(BtakaException::new);
        /*
        return getUserInfo(code, state)
                .flatMap(snsUser ->
                    userOauthService.get(getSite(), snsUser.getId())
                            .filter(Objects::nonNull)
                            .then(Mono.just(snsUser))
                )
                .doOnError(BtakaException::new);
         */
    }

    @Override
    public Mono<SnsUser> userInfo(String accessToken) {
        return getUserInfo(accessToken);
    }

    /*
    * todo Register 전에 등록된 사용자인지 확인해봐야한다.
     */
    @Override
    public Mono<SnsUser> register(String userOid, String code, String state, String nonce) {
        return getToken(code, state, getRegisterRedirectUrl())
                .flatMap(token -> {
                    Map<String, Object> tokenInfoMap = convertTokenToMap(token);
                    return getUserInfo(tokenInfoMap)
                            .map(map -> convertUserInfo(token, map));
                })
                .flatMap(snsUser -> userOauthService.get(this.getSite(), userOid)
                        .filter(userOauthDTO -> !Objects.isNull(userOauthDTO.getOauthId()))
                        .map(userOauthDTO -> snsUser)
                        .switchIfEmpty(
                                userService.findByOid(userOid)
                                        .flatMap(user -> userOauthService.save(UserOauthDTO.builder()
                                                .userOid(userOid)
                                                .oauthId(snsUser.getId())
                                                .oauthSite(getSite())
                                                .email(snsUser.getEmail())
                                                .build()))
                                        .then(Mono.just(snsUser))
                        ));
        /*
        return userInfo(code, state, nonce)
                .flatMap(snsUser ->
                        userOauthService.get(this.getSite(), userOid)
                            .filter(userOauthDTO -> !Objects.isNull(userOauthDTO.getOauthId()))
                            .map(userOauthDTO -> snsUser)
                            .switchIfEmpty(
                                    userService.findByOid(userOid)
                                            .flatMap(user -> userOauthService.save(UserOauthDTO.builder()
                                                    .userOid(userOid)
                                                    .oauthId(snsUser.getId())
                                                    .oauthSite(getSite())
                                                    .email(snsUser.getEmail())
                                                    .build()))
                                            .then(Mono.just(snsUser))
                            )
                )
                .doOnError(BtakaException::new);

         */
    }
}
