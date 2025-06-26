package org.example.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.example.dto.ScanRequestDTO;
import org.example.dto.ScanResultDTO;
import org.example.dto.ValidationErrorDTO;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ScannerService {

    private final ValidationService validationService;
    private final FileFinder fileFinder;

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
            return fileFinder.findFiles(startDir, mask);
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private String getStartDirectory(String path) {
        return Paths.get(path).toAbsolutePath().toString();
    }

}
