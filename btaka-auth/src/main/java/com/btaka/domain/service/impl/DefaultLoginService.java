package com.btaka.domain.service.impl;

import com.btaka.board.common.dto.ResponseDTO;
import com.btaka.board.common.dto.User;
import com.btaka.cache.dto.AuthInfo;
import com.btaka.cache.service.AuthCacheService;
import com.btaka.common.exception.BtakaException;
import com.btaka.common.util.CookieUtil;
import com.btaka.constant.AuthErrorCode;
import com.btaka.constant.AuthParamConst;
import com.btaka.constant.UserParamConst;
import com.btaka.domain.service.UserOauthService;
import com.btaka.domain.service.UserService;
import com.btaka.domain.web.dto.AuthRequestDTO;
import com.btaka.jwt.JwtService;
import com.btaka.domain.service.LoginService;
import com.btaka.jwt.dto.JwtDTO;
import com.mongodb.internal.HexUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service("defaultLoginService")
@RequiredArgsConstructor
public class DefaultLoginService implements LoginService {

    // todo
    // config server로 이전 해야한다.
    private final int MAX_FAIL_COUNT = 5;
    private final long LOCK_PERIOD = 60 * 5;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserOauthService userOauthService;
    private final AuthCacheService authCacheService;

    private AuthInfo convertJwtToAuthInfo(JwtDTO jwtDTO) {
        return AuthInfo.builder()
                .userId(jwtDTO.getUserId())
                .loginAt(jwtDTO.getLoginAt())
                .expiredAt(jwtDTO.getExpiredAt())
                .accessToken(jwtDTO.getAccessToken())
                .build();
    }

    private Mono<ResponseDTO> processLogin(User user, ServerWebExchange webExchange) {
        return Mono.just(user)
                .flatMap(dto -> {
                    JwtDTO jwtDTO = jwtService.generateToken(user);
                    String sid = webExchange.getRequest().getId();
                    String encodeSid = HexUtils.toHex(sid.getBytes(StandardCharsets.UTF_8));
                    CookieUtil.saveSessionCookie(webExchange.getResponse(), AuthParamConst.PARAM_AUTH_SESSION_ID.getKey(), encodeSid);

                    return authCacheService.saveAuthInfo(encodeSid, convertJwtToAuthInfo(jwtDTO))
                            .then(Mono.just(ResponseDTO
                                    .builder()
                                    .set(UserParamConst.PARAM_USER_OBJECT_ID.getKey(), user.getOid())
                                    .set(AuthParamConst.PARAM_AUTH_ACCESS_TOKEN.getKey(), jwtDTO.getAccessToken())
                                    .build())
                            );
                });
    }

    protected boolean validateUser(AuthRequestDTO authRequestDTO, User user) {

        if (user.isLocked()) {
            LocalDateTime lockedTime = user.getLockedTime();
            LocalDateTime unlockTime = lockedTime.plusSeconds(LOCK_PERIOD);
            if (lockedTime.isAfter(unlockTime)) {
                unLockUser(user);
            } else {
                throw new BtakaException(AuthErrorCode.USER_LOCKED);
            }
        }

        if (!checkPassword(authRequestDTO.getPassword(), user.getPassword())) {

            addFailCount(user);

            if (user.getFailCount() + 1 >= MAX_FAIL_COUNT) {
                lockUser(user);
                throw new BtakaException(AuthErrorCode.USER_LOCKED);
            }
            throw new BtakaException(AuthErrorCode.PASSWORD_NOT_MATCH);
        }
        loginSuccess(user);
        return true;
    }

    protected boolean checkPassword(String inputPassword, String savedPassword) {
        return passwordEncoder.matches(inputPassword, savedPassword);
    }

    protected void lockUser(User user) {
        userService.lockUser(user).subscribe();
    }

    protected void unLockUser(User user) {
        userService.unlockUser(user).subscribe();
    }

    protected void addFailCount(User user) {
        userService.loginFail(user).subscribe();
    }

    protected void loginSuccess(User user) {
        userService.loginSuccess(user).subscribe();
    }

    @Override
    public Mono<ResponseDTO> auth(ServerWebExchange webExchange, AuthRequestDTO authRequestDTO) {
        return userService.findByEmail(authRequestDTO.getEmail())
                .switchIfEmpty(Mono.error(new BtakaException(AuthErrorCode.USER_NOT_FOUND)))
                .filter(user -> validateUser(authRequestDTO, user))
                .switchIfEmpty(Mono.error(new BtakaException(AuthErrorCode.PASSWORD_NOT_MATCH)))
                .flatMap(user -> this.processLogin(user, webExchange))
                .onErrorResume(throwable ->
                    Mono.error(new BtakaException(HttpStatus.UNAUTHORIZED, throwable))
                );
    }

    @Override
    public Mono<ResponseDTO> authByOauth(ServerWebExchange webExchange, AuthRequestDTO authRequestDTO) {
        return userOauthService.getByOauthId(authRequestDTO.getOauthId())
                .filter(userOauthDTO -> !Objects.isNull(userOauthDTO.getOauthId()) && !Objects.isNull(userOauthDTO.getUserOid()))
                .switchIfEmpty(Mono.error(new BtakaException(AuthErrorCode.NOT_REGISTER_OAUTH_USER)))
                .flatMap(userOauthDTO -> userService.findByOid(userOauthDTO.getUserOid()))
                .flatMap(user -> this.processLogin(user, webExchange))
                .switchIfEmpty(Mono.just(new ResponseDTO()))
                .onErrorResume(throwable ->
                        Mono.error(new BtakaException(HttpStatus.INTERNAL_SERVER_ERROR, throwable))
                );
    }

    @Override
    public Mono<ResponseDTO> isLogin(String psid) {
        if (Objects.isNull(psid)) {
            return Mono.error(new BtakaException(AuthErrorCode.NOT_LOGIN));
        }
        return authCacheService.isLogin(psid)
                .filter(authCacheDTO -> !Objects.isNull(authCacheDTO) && !Objects.isNull(authCacheDTO.getSid()))
                .flatMap(authCacheDTO -> Mono.just(ResponseDTO
                        .builder()
                        .set(UserParamConst.PARAM_USER_ID.getKey(), authCacheDTO.getAuthInfo().getUserId())
                        .set(AuthParamConst.PARAM_AUTH_ACCESS_TOKEN.getKey(), authCacheDTO.getAuthInfo().getAccessToken())
                        .build()))
                .switchIfEmpty(Mono.just(ResponseDTO.builder().success(false).build()))
                .onErrorResume(throwable ->
                        Mono.error(new BtakaException(HttpStatus.INTERNAL_SERVER_ERROR, throwable))
                );
    }

    @Override
    public Mono<ResponseDTO> isLogin(String psid, String accessToken) {
        if (!Objects.isNull(accessToken) && jwtService.isValidToken(accessToken)) {
            return Mono.just(accessToken)
                    .flatMap(token ->
                        authCacheService.isTokenAvailable(token)
                                .flatMap(authCacheDTO -> Mono.just(ResponseDTO
                                        .builder()
                                        .set(UserParamConst.PARAM_USER_ID.getKey(), authCacheDTO.getAuthInfo().getUserId())
                                        .set(AuthParamConst.PARAM_AUTH_ACCESS_TOKEN.getKey(), authCacheDTO.getAuthInfo().getAccessToken())
                                        .build())
                                )
                                .switchIfEmpty(Mono.error(new BtakaException(AuthErrorCode.NOT_LOGIN)))
                    )
                    .onErrorResume(throwable ->
                            Mono.error(new BtakaException(throwable))
                    );
        }
        return isLogin(psid);
    }

    @Override
    public Mono<ResponseDTO>  logout(String psid, ServerWebExchange webExchange) {
        return authCacheService.expireToken(psid)
                .flatMap(isSuccess -> {
                    webExchange.getResponse().addCookie(
                            ResponseCookie
                                    .from(AuthParamConst.PARAM_AUTH_SESSION_ID.getKey(), "-")
                                    .httpOnly(true)
                                    .maxAge(0)
                                    .build());
                    /*
                    webExchange.getResponse().getHeaders().clear();*/
                    return Mono.just(ResponseDTO.builder().build());
                })
                .onErrorResume(throwable ->
                        Mono.error(new BtakaException(HttpStatus.INTERNAL_SERVER_ERROR, throwable))
                );
    }

}
