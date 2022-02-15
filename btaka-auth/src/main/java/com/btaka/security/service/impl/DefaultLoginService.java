package com.btaka.security.service.impl;

import com.btaka.common.dto.User;
import com.btaka.domain.service.UserService;
import com.btaka.dto.AuthRequestDTO;
import com.btaka.dto.AuthResponseDTO;
import com.btaka.jwt.JwtService;
import com.btaka.security.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@Service("defaultLoginService")
@RequiredArgsConstructor
public class DefaultLoginService implements LoginService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public Mono<AuthResponseDTO> auth(AuthRequestDTO authRequestDTO) {
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
                )
                .onErrorResume(throwable ->
                    Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, throwable.getLocalizedMessage()))
                );
    }
}
