package com.btaka.oauth.factory;

import com.btaka.oauth.service.OauthSnsService;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SnsServiceFactory {


    private static final Map<String, OauthSnsService> serviceMap = new ConcurrentHashMap<>();

    public SnsServiceFactory add(OauthSnsService oauthSnsService) {
        serviceMap.put(oauthSnsService.getSite(), oauthSnsService);
        return this;
    }

    public OauthSnsService get(String siteName) {
        return serviceMap.getOrDefault(siteName, null);
    }

    public Map<String, OauthSnsService> getAll() {
        return serviceMap;
    }
}
