package org.example.core.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.core.cache.ScanResultCache;
import org.example.core.services.filters.FileSearchFilter;
import org.example.core.services.filters.FilterFactory;
import org.example.core.util.MaskToRegexConverter;
import org.example.dto.SearchParams;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class FileFinder {

    private final FileScanner scanner;
    private final FileFilterService filterService;
    private final ScanResultCache cache;

    public List<String> findFiles(String startDir, String mask, SearchParams searchParams)
            throws IOException, InterruptedException {

        String cacheKey = startDir + "|" + mask + "|" + searchParams;

        List<String> cachedResult = cache.get(cacheKey);
        if (cachedResult != null) {
            log.info("Cache hit for key: {}", cacheKey);
            return cachedResult;
        }

        Pattern pattern = Pattern.compile(MaskToRegexConverter.convert(mask));
        FileSearchFilter filter = FilterFactory.createFilter(searchParams);
        List<Path> allFiles = scanner.scan(startDir);
        List<String> result = filterService.applyFilters(allFiles, pattern, filter);

        log.info("Cache put for key: {}", cacheKey);
        cache.put(cacheKey, result);

        return result;
    }

}
