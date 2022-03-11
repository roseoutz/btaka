package com.btaka.domain.web;

import com.btaka.domain.dto.ConfigResponseDTO;
import com.btaka.domain.service.ConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1")
public class BtakaConfigApiController {

    @Resource(name = "${btaka.bean.service.config:defaultConfigService}")
    private ConfigService configService;

    @GetMapping("/config/{key}")
    public Mono<ResponseEntity<ConfigResponseDTO>> get(@PathVariable("key") String key) {
        return configService.get(key)
                .flatMap(dto -> Mono.just(
                        ResponseEntity.ok(ConfigResponseDTO
                                .builder()
                                .set(dto)
                                .build()))
                );
    }

    @GetMapping("/configs/{group}")
    public Mono<ResponseEntity<ConfigResponseDTO>> getByGroup(@PathVariable("group") String group) {
        return configService.getByGroup(group)
                .flatMap(configDTOList -> {
                    ConfigResponseDTO responseDTO = ConfigResponseDTO.builder().set(configDTOList).build();
                    return Mono.just(
                        ResponseEntity.ok(responseDTO)
                    );
                });
    }


}
