package org.example.services;

import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.example.util.MaskToRegexConverter.convert;

@Component
class FileFinder {

    List<String> findFiles(String startDir, String mask) throws IOException, InterruptedException, ExecutionException {
        Pattern pattern = compilePattern(mask);
        List<Path> allFiles = listFiles(startDir);

        return filterFilesByPattern(allFiles, pattern);
    }

    private Pattern compilePattern(String mask) {
        return Pattern.compile(convert(mask));
    }

    private List<Path> listFiles(String startDir) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(startDir))) {
            return stream.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
    }

    private List<String> filterFilesByPattern(List<Path> paths, Pattern pattern) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().factory());
        try {

            List<Future<String>> futures = new ArrayList<>();
            for (Path path : paths) {
                futures.add(executor.submit(() -> {
                    String fileName = path.getFileName().toString();
                    if (pattern.matcher(fileName).matches()) {
                        return path.toString();
                    } else {
                        return null;
                    }
                }));
            }

            List<String> matchedFiles = new ArrayList<>();
            for (Future<String> future : futures) {
                String result = future.get();
                if (result != null) {
                    matchedFiles.add(result);
                }
            }

            return matchedFiles;
        } finally {
            executor.shutdown();
        }
    }

}
