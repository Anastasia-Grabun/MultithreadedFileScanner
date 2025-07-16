package org.example.core.services;

import org.example.core.services.filters.FileSearchFilter;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

@Component
class FileFilterService {

    public List<String> applyFilters(List<Path> files, Pattern pattern, FileSearchFilter filter)
            throws InterruptedException {

        ExecutorService executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().factory());
        try {
            List<Future<String>> futures = files.stream()
                    .map(path -> executor.submit(() ->
                            matches(path, pattern, filter) ? path.toString() : null))
                    .toList();

            List<String> result = new ArrayList<>();
            for (Future<String> future : futures) {
                try {
                    String value = future.get();
                    if (value != null) result.add(value);
                } catch (ExecutionException e) {
                }
            }
            return result;
        } finally {
            executor.shutdown();
        }
    }

    private boolean matches(Path path, Pattern pattern, FileSearchFilter filter) {
        try {
            return pattern.matcher(path.getFileName().toString()).matches() && filter.test(path);
        } catch (IOException e) {
            return false;
        }
    }

}
