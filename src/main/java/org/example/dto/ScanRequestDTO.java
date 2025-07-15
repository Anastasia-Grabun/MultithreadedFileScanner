package org.example.dto;

public record ScanRequestDTO(
        String path,
        String mask,
        SearchParams searchParams)
{}


