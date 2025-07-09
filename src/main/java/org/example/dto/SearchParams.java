package org.example.dto;

import java.time.LocalDate;

public record SearchParams(
        String namePattern,
        Long minSize,
        Long maxSize,
        LocalDate modifiedAfter,
        String contentContains
) {}
