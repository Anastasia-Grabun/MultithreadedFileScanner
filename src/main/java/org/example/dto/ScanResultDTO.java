package org.example.dto;

import java.util.List;

public record ScanResultDTO(List<String> files, List<ValidationErrorDTO> errors) {}


