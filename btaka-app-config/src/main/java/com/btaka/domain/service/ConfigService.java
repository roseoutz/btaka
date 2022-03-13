package com.btaka.domain.service;

import com.btaka.config.dto.ConfigDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ConfigService {
    Mono<ConfigDTO> get(String key);

    Mono<List<ConfigDTO>> getByGroup(String group);

    Mono<List<ConfigDTO>> getAll();

    Mono<ConfigDTO> save(ConfigDTO configDTO);

    Mono<ConfigDTO> update(ConfigDTO configDTO);
}
