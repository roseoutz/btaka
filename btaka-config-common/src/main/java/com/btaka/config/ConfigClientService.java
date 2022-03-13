package com.btaka.config;

import com.btaka.config.dto.ConfigResponseDTO;
import reactor.core.publisher.Mono;

public interface ConfigClientService {

    Mono<ConfigResponseDTO> getConfig(String key);
    Mono<ConfigResponseDTO> getConfigs(String group);

}
