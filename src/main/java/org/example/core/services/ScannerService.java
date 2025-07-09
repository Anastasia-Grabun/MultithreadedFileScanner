package org.example.core.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.example.dto.ScanRequestDTO;
import org.example.dto.ScanResultDTO;
import org.example.dto.ValidationErrorDTO;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ScannerService {

    private final ValidationService validationService;
    private final FilterValidationService filterValidationService;
    private final FileFinder fileFinder;

    public ScanResultDTO scan(ScanRequestDTO request) {
        List<ValidationErrorDTO> validationErrors = collectValidationErrors(request);
        if (!validationErrors.isEmpty()) {
            return ScanResultDTO.withErrors(validationErrors);
        }

        return performFileScan(request);
    }

    private List<ValidationErrorDTO> collectValidationErrors(ScanRequestDTO request) {
        List<ValidationErrorDTO> errors = new ArrayList<>();
        errors.addAll(validationService.validate(request));
        errors.addAll(filterValidationService.validate(request));

        return errors;
    }

    private ScanResultDTO performFileScan(ScanRequestDTO request) {
        try {
            List<String> files = fileFinder.findFiles(
                    getStartDirectory(request.path()),
                    request.mask(),
                    request.searchParams()
            );
            return ScanResultDTO.withFiles(files);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            return errorResult("File search interrupted or IO error: " + e.getMessage());
        }
    }

    private ScanResultDTO errorResult(String message) {
        return ScanResultDTO.withErrors(List.of(
                new ValidationErrorDTO("SCAN_ERROR", message)
        ));
    }

    private String getStartDirectory(String path) {
        return Paths.get(path).toAbsolutePath().toString();
    }

}
