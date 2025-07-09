package org.example.rest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.example.dto.ScanRequestDTO;
import org.example.dto.ScanResultDTO;
import org.example.core.services.ScannerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scan/api/v1")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class FileScanController {

    private final ScannerService scannerService;

    @PostMapping
    public ScanResultDTO scan(@RequestBody ScanRequestDTO scanRequestDTO) {
        return scannerService.scan(scanRequestDTO);
    }

}
