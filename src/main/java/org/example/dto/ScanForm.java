package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScanForm {

    private String path;
    private String mask;
    private String contentContains;
    private Long minSize;
    private Long maxSize;
    private LocalDate modifiedAfter;

}

