package com.btaka.domain.web;

import com.btaka.dto.IndexResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/auth/kakao")
public class BtakaKakaoApiController {

    @Value(value = "${btaka.social.kakao.clientId}")
    private String clientId;
    @Value(value = "${btaka.social.kakao.clientSecret}")
    private String clientSecret;
    @Value(value = "${btaka.social.kakao.requestURl}")
    private String requestURl;

    @GetMapping
    public Mono<ResponseEntity<IndexResponseDTO>> index() {
        return Mono.just(ResponseEntity
                .status(HttpStatus.OK).body(IndexResponseDTO
                                .builder()
                                .socialLoginUrl(requestURl + "?client_id=" + clientId + "&redirect_uri=http://localhost:14000/oauth&respose_type=code")
                                .build()));
    }

}
