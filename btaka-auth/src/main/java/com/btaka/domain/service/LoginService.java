package com.btaka.domain.service;

import com.btaka.domain.web.dto.AuthRequestDTO;
import com.btaka.domain.web.dto.ResponseDTO;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface LoginService {

    Mono<ResponseDTO> auth(ServerWebExchange webExchange, AuthRequestDTO authRequestDTO);

    Mono<ResponseDTO> authByOauth(ServerWebExchange webExchange, AuthRequestDTO authRequestDTO);

    Mono<ResponseDTO> isLogin(String psid);

}