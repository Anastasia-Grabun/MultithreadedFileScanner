package org.example.core.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.example.core.services.filters.FileSearchFilter;
import org.example.core.services.filters.FilterFactory;
import org.example.core.util.MaskToRegexConverter;
import org.example.dto.SearchParams;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class FileFinder {

    private final FileScanner scanner;
    private final FileFilterService filterService;
    private final FilterFactory filterFactory;

    public List<String> findFiles(String startDir, String mask, SearchParams searchParams)
            throws IOException, InterruptedException {

        Pattern pattern = Pattern.compile(MaskToRegexConverter.convert(mask));
        FileSearchFilter filter = filterFactory.createFilter(searchParams);
        List<Path> allFiles = scanner.scan(startDir);

        return filterService.applyFilters(allFiles, pattern, filter);
    }

}
