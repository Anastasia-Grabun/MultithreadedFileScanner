package org.example.core.services.filters;

import java.nio.file.Path;
import java.util.regex.Pattern;

class NameSearchFilter implements FileSearchFilter {
    private final Pattern pattern;

    public NameSearchFilter(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public boolean test(Path file) {
        return pattern.matcher(file.getFileName().toString()).matches();
    }

}
