package com.btaka.domain.web;

import com.btaka.board.common.dto.ResponseDTO;
import com.btaka.common.exception.BtakaException;
import com.btaka.constant.ConfigErrorCode;
import com.btaka.domain.dto.ConfigDTO;
import com.btaka.domain.service.ConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/config")
public class BtakaConfigApiController {

    @Resource(name = "${btaka.bean.service.config:defaultConfigService}")
    private ConfigService configService;

    @GetMapping("/{key}")
    public Mono<ResponseEntity<ResponseDTO>> get(@PathVariable("key") String key) {
        return configService.get(key)
                .flatMap(dto -> Mono.just(
                        ResponseEntity.ok(ResponseDTO
                                .builder()
                                .set("key", dto.getKey())
                                .set("value", dto.getValue())
                                .build()))
                );
    }

    @GetMapping
    public Mono<ResponseEntity<ResponseDTO>> get() {
        return configService.getAll()
                .flatMap(configDTOList -> {
                    ResponseDTO.Builder responseDTO = ResponseDTO.builder();
                    for (ConfigDTO dto: configDTOList) {
                        responseDTO.set("key", dto.getKey()).set("value", dto.getValue());
                    }
                    return Mono.just(
                        ResponseEntity.ok(responseDTO.build())
                    );
                });
    }


}
