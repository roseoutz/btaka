package com.btaka.security.service;

import com.btaka.dto.AuthRequestDTO;
import com.btaka.dto.AuthResponseDTO;
import reactor.core.publisher.Mono;

public interface LoginService {

    Mono<AuthResponseDTO> auth(AuthRequestDTO authRequestDTO);

}
