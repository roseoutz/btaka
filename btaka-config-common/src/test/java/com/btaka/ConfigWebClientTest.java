package com.btaka;

import com.btaka.config.ConfigClientService;
import com.btaka.config.impl.DefaultConfigClientService;
import org.junit.jupiter.api.Test;

public class ConfigWebClientTest {

    private String configServerUrl = "http://127.0.0.1:16000/api/v1/";
    private String getOneConfig = "config/";
    private String getGroupConfig = "configs/";

    private ConfigClientService configClientService = new DefaultConfigClientService(configServerUrl + getOneConfig, configServerUrl + getGroupConfig);

    @Test
    public void getOneConfig() throws InterruptedException {
        configClientService.getConfigs("validation.password")
                .doOnNext(configResponseDTO -> System.out.println(configResponseDTO.toString()))
                .block();

        Thread.sleep(1000);
    }
}
