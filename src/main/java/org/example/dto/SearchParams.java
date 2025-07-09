package org.example.dto;

import java.time.LocalDate;

public record SearchParams(
        Long minSize,
        Long maxSize,
        LocalDate modifiedAfter,
        String contentContains
) {}
