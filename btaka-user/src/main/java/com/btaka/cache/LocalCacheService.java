package com.btaka.cache;

import java.util.concurrent.ConcurrentHashMap;

public class LocalCacheService implements CacheService{

    private final ConcurrentHashMap cacheMap = new ConcurrentHashMap();

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public String getString(String key) {
        return null;
    }

    @Override
    public boolean getBoolean(String key) {
        return false;
    }

    @Override
    public int getInt(String key) {
        return 0;
    }

    @Override
    public void set(String key, Object value) {

    }
}
