package org.example.core.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ScanResultCache {

    private final Cache<String, List<String>> cache;

    public ScanResultCache(ScanCacheProperties properties) {
        this.cache = CacheBuilder.newBuilder()
                .expireAfterWrite(properties.getExpireAfterWriteMinutes(), TimeUnit.MINUTES)
                .maximumSize(properties.getMaximumSize())
                .build();
    }

    public List<String> get(String key) {
        return cache.getIfPresent(key);
    }

    public void put(String key, List<String> value) {
        cache.put(key, value);
    }

}
