package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScanForm {
    private String path;
    private String mask;

    private String contentContains;
    private Long minSize;
    private Long maxSize;
    private String modifiedAfter;
}

