package com.btaka.config.impl;

import com.btaka.common.exception.BtakaException;
import com.btaka.config.ConfigClientService;
import org.springframework.web.reactive.function.client.WebClient;


public class DefaultConfigClientService implements ConfigClientService {

    private final String GET_CONFIG_URL = "/group/";
    private final String GET_GROUP_CONFIG_URL = "/groups/";

    private final String configServerUrl;

    public DefaultConfigClientService(String configServerUrl) {
        this.configServerUrl = configServerUrl;
    }

    protected WebClient getConfigClient(String key) {
        return WebClient.create(configServerUrl + GET_CONFIG_URL + key);
    }

    protected WebClient getGroupConfigClient(String group) {
        return WebClient.create(configServerUrl + GET_GROUP_CONFIG_URL + group);
    }

    @Override
    public void getConfig(String key) {
        getConfigClient(key)
                .get()
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(BtakaException::new);
    }

    @Override
    public void getConfigs(String group) {
        getGroupConfigClient(group)
                .get()
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(BtakaException::new);

    }

}
