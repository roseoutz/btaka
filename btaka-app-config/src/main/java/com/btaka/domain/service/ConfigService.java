package com.btaka.domain.service;

import com.btaka.domain.dto.ConfigDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ConfigService {
    Mono<ConfigDTO> get(String key);

    Mono<List<ConfigDTO>> getByGroup(String group);

    Mono<List<ConfigDTO>> getAll();
}
