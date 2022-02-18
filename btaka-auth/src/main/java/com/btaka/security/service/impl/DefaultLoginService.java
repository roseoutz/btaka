package com.btaka.security.service.impl;

import com.btaka.board.common.dto.User;
import com.btaka.cache.dto.AuthInfo;
import com.btaka.cache.service.AuthCacheService;
import com.btaka.domain.service.UserService;
import com.btaka.dto.AuthRequestDTO;
import com.btaka.dto.AuthResponseDTO;
import com.btaka.jwt.JwtService;
import com.btaka.security.service.LoginService;
import io.netty.handler.codec.http.cookie.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service("defaultLoginService")
@RequiredArgsConstructor
public class DefaultLoginService implements LoginService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuthCacheService authCacheService;

    @Override
    public Mono<AuthResponseDTO> auth(ServerWebExchange webExchange, AuthRequestDTO authRequestDTO) {
        return userService.findByEmail(authRequestDTO.getEmail())
                .filter(userInfo -> passwordEncoder.matches(authRequestDTO.getPassword(), userInfo.getPassword()))
                .flatMap(userInfo -> Mono.just(userInfo)
                        .map(user -> User.builder()
                                .username(user.getUsername())
                                .email(user.getEmail())
                                .mobile(user.getMobile())
                                .roles(user.getRoles())
                                .build())
                        .map(user -> new AuthResponseDTO(jwtService.generateToken(user)))
                        .doOnSuccess(responseDto ->
                            Mono.just(responseDto)
                                .subscribeOn(Schedulers.boundedElastic())
                                    .flatMap(response -> {
                                        String sid = webExchange.getRequest().getId();
                                        String encodeSid = Base64.getEncoder().encodeToString(sid.getBytes(StandardCharsets.UTF_8));
                                        return authCacheService.saveAuthInfo(encodeSid, AuthInfo.builder().build());
                                    })
                                    .subscribe(cacheDTO -> {
                                        webExchange.getResponse().addCookie(
                                                ResponseCookie
                                                        .from("psid", cacheDTO.getSid())
                                                        .httpOnly(true)
                                                        .build()
                                        );
                                    })
                        )
                )
                .onErrorResume(throwable ->
                    Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, throwable.getLocalizedMessage()))
                );
    }

}
