package com.mitdy.core.util;

import java.util.Date;

import com.whalin.MemCached.MemCachedClient;

public class MemcachedUtils {

    private static MemCachedClient cachedClient;

    static {
        if (cachedClient == null) {
            cachedClient = new MemCachedClient();
        }
    }

    public static boolean add(String key, Object value) {
        return cachedClient.set(key, value);
    }

    public static boolean add(String key, Object value, int milliseconds) {
        return cachedClient.set(key, value, milliseconds);
    }

    public static boolean remove(String key) {
        return cachedClient.delete(key);
    }

    @SuppressWarnings("deprecation")
    public static boolean remove(String key, int milliseconds) {
        return cachedClient.delete(key, milliseconds, new Date());
    }

    public static boolean update(String key, Object value, int milliseconds) {
        return cachedClient.replace(key, value, milliseconds);
    }

    public static boolean update(String key, Object value) {
        return cachedClient.replace(key, value);
    }

    public static Object get(String key) {
        return cachedClient.get(key);
    }

}