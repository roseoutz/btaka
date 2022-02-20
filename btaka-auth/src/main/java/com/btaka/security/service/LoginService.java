package com.btaka.security.service;

import com.btaka.dto.AuthRequestDTO;
import com.btaka.dto.AuthResponseDTO;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface LoginService {

    Mono<AuthResponseDTO> auth(ServerWebExchange webExchange, AuthRequestDTO authRequestDTO);

    Mono<AuthResponseDTO> isLogin(String psid);

}
