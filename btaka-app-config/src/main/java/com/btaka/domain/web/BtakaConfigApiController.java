package com.btaka.domain.web;

import com.btaka.config.dto.ConfigDTO;
import com.btaka.config.dto.ConfigResponseDTO;
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
                .map(dto -> ResponseEntity.ok(ConfigResponseDTO
                        .builder()
                        .set(dto)
                        .build())
                );
    }

    @GetMapping("/configs/{group}")
    public Mono<ResponseEntity<ConfigResponseDTO>> getByGroup(@PathVariable("group") String group) {
        return configService.getByGroup(group)
                .map(configDTOList -> ResponseEntity.ok(ConfigResponseDTO.builder().set(configDTOList).build()));
    }

    @PostMapping("/config")
    public Mono<ResponseEntity<ConfigResponseDTO>> add(@RequestBody ConfigDTO configDTO) {
        return configService.save(configDTO)
                .map(dto -> {
                    ConfigResponseDTO responseDTO = ConfigResponseDTO
                            .builder()
                            .set(dto)
                            .build();
                    return ResponseEntity.ok(responseDTO);
                });
    }

}
