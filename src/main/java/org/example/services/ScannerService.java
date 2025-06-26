package org.example.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.example.dto.ScanRequestDTO;
import org.example.dto.ScanResultDTO;
import org.example.dto.ValidationErrorDTO;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.example.util.MaskToRegexConverter.convert;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ScannerService {

    private final ValidationService validationService;

    public ScanResultDTO scan(ScanRequestDTO scanRequestDTO) {
        List<ValidationErrorDTO> errors = validationService.validate(scanRequestDTO);
        if (hasErrors(errors)) {
            return new ScanResultDTO(Collections.emptyList(), errors);
        }

        List<String> files = performScan(scanRequestDTO.path(), scanRequestDTO.mask());

        return new ScanResultDTO(files, Collections.emptyList());
    }

    private boolean hasErrors(List<ValidationErrorDTO> errors) {
        return errors != null && !errors.isEmpty();
    }

    private List<String> performScan(String path, String mask) {
        String startDir = getStartDirectory(path);
        try {
            return findFiles(startDir, mask);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private String getStartDirectory(String path) {
        return Paths.get(path).toAbsolutePath().toString();
    }

    private List<String> findFiles(String startDir, String mask) throws IOException {
        final Pattern pattern = Pattern.compile(convert(mask));

        try (Stream<Path> stream = Files.walk(Paths.get(startDir))) {
            return stream
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(path -> pattern.matcher(Paths.get(path).getFileName().toString()).matches())
                    .collect(Collectors.toList());
        }
    }

}
