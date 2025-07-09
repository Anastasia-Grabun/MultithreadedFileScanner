package org.example.core.services.filters;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;

class SizeSearchFilter implements FileSearchFilter {
    private final Long minSize;
    private final Long maxSize;

    public SizeSearchFilter(Long minSize, Long maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    @Override
    public boolean test(Path file) throws IOException {
        long size = Files.size(file);
        return (minSize == null || size >= minSize) &&
                (maxSize == null || size <= maxSize);
    }

}
