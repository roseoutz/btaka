package com.btaka.domain.service.impl;

import com.btaka.common.exception.BtakaException;
import com.btaka.common.service.AbstractDataService;
import com.btaka.constant.ConfigErrorCode;
import com.btaka.domain.dto.ConfigDTO;
import com.btaka.domain.entity.ConfigEntity;
import com.btaka.domain.repo.ConfigRepository;
import com.btaka.domain.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service("defaultConfigService")
public class DefaultConfigService extends AbstractDataService<ConfigEntity, ConfigDTO> implements ConfigService {

    public DefaultConfigService() {
        super(ConfigEntity.class, ConfigDTO.class);
    }

    @Autowired
    private ConfigRepository configRepository;

    @Override
    public Mono<ConfigDTO> get(String key) {
        return configRepository.findByKey(key)
                .flatMap(entity -> Mono.just(toDto(entity))
                .switchIfEmpty(Mono.error(new BtakaException(ConfigErrorCode.CONFIG_KEY_NOT_FOUND))));
    }

    @Override
    public Mono<List<ConfigDTO>> getByGroup(String group) {
        return configRepository.findByGroup(group)
                .flatMap(entity -> Mono.just(toDto(entity)))
                .switchIfEmpty(Mono.error(new BtakaException(ConfigErrorCode.CONFIG_GROUP_NOT_FOUND)))
                .collectList();

    }

    @Override
    public Mono<List<ConfigDTO>> getAll() {
        return configRepository.findAll()
                .flatMap(entity -> Mono.just(toDto(entity)))
                .switchIfEmpty(Flux.error(new BtakaException(ConfigErrorCode.CONFIG_KEY_NOT_FOUND)))
                .collectList();
    }
}
