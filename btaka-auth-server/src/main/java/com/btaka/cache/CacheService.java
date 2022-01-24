package com.btaka.cache;

public interface CacheService {

    void set(String key, Object value);

    Object get(String key);

    String getString(String key);

    boolean getBoolean(String key);

    int getInt(String key);
}
