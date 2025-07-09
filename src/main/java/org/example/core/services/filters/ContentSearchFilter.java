package org.example.core.services.filters;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;

class ContentSearchFilter implements FileSearchFilter {

    private final String searchText;

    public ContentSearchFilter(String searchText) {
        this.searchText = searchText;
    }

    @Override
    public boolean test(Path file) throws IOException {
        if (!isTextFile(file)) return false;
        return Files.lines(file).anyMatch(line -> line.contains(searchText));
    }

    private boolean isTextFile(Path file) {
        String name = file.toString().toLowerCase();
        return name.endsWith(".txt") || name.endsWith(".log");
    }

}
