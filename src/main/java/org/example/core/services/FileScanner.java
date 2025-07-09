package org.example.core.services;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
class FileScanner {

    public List<Path> scan(String startDir) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(startDir))) {
            return stream.filter(Files::isRegularFile).collect(Collectors.toList());
        }
    }

}

