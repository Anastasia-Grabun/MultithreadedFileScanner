package org.example.core.services.filters;

import java.io.IOException;
import java.nio.file.Path;

@FunctionalInterface
public interface FileSearchFilter {

    boolean test(Path file) throws IOException;

    default FileSearchFilter and(FileSearchFilter other) {
        return file -> this.test(file) && other.test(file);
    }

}
