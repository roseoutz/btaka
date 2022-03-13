package com.btaka.config.impl;

import com.btaka.common.exception.BtakaException;
import com.btaka.config.ConfigClientService;
import com.btaka.config.dto.ConfigResponseDTO;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


public class DefaultConfigClientService implements ConfigClientService {

    private final String oneConfigGetUrl;
    private final String groupConfigGetUrl;

    public DefaultConfigClientService( String oneConfigGetUrl, String groupConfigGetUrl) {
        this.oneConfigGetUrl = oneConfigGetUrl;
        this.groupConfigGetUrl = groupConfigGetUrl;
    }

    @Override
    public Mono<ConfigResponseDTO> getConfig(String key) {
        return WebClient.create(oneConfigGetUrl + "/" + key)
                .get()
                .retrieve()
                .bodyToMono(ConfigResponseDTO.class)
                .doOnError(BtakaException::new);
    }

    @Override
    public Mono<ConfigResponseDTO> getConfigs(String group) {
        return WebClient.create(groupConfigGetUrl + "/" + group)
                .get()
                .retrieve()
                .bodyToMono(ConfigResponseDTO.class)
                .doOnError(BtakaException::new);
    }

}
