package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Collections;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ScanResultDTO(
        List<String> files,
        List<ValidationErrorDTO> errors
) {
    public static ScanResultDTO withFiles(List<String> files) {
        return new ScanResultDTO(files, Collections.emptyList());
    }

    public static ScanResultDTO withErrors(List<ValidationErrorDTO> errors) {
        return new ScanResultDTO(Collections.emptyList(), errors);
    }

}


