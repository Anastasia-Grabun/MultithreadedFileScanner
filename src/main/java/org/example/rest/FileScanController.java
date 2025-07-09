package org.example.rest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.example.core.services.BatchScanManager;
import org.example.dto.ListRequestDTO;
import org.example.dto.ScanResultDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/scan/api/v1")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class FileScanController {

    private final BatchScanManager batchScanManager;

    @PostMapping()
    public List<ScanResultDTO> scanBatch(@RequestBody ListRequestDTO request) {
        return batchScanManager.process(request);
    }

}
