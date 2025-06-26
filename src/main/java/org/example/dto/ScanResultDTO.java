package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ScanResultDTO(List<String> files, List<ValidationErrorDTO> errors) {}


