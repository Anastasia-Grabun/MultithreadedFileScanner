package org.example.rest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.example.dto.ScanResultDTO;
import org.example.services.ScannerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/scan/api/v1")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class FileScanController {

    private final ScannerService scannerService;


    @GetMapping("/scan")
    public ScanResultDTO scan(@RequestParam String path, @RequestParam String mask) {
        List<String> result = scannerService.scan(path, mask);
        return new ScanResultDTO(result);
    }

}
