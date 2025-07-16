package org.example.core.cache;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "cache")
public class ScanCacheProperties {

    private long expireAfterWriteMinutes;
    private long maximumSize;

}

