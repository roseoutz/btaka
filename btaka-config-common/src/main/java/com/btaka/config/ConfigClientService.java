package com.btaka.config;

public interface ConfigClientService {

    void getConfig(String key);
    void getConfigs(String group);

}
