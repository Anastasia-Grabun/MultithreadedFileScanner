package org.example.core.services;

import lombok.RequiredArgsConstructor;
import org.example.dto.ListRequestDTO;
import org.example.dto.ScanRequestDTO;
import org.example.dto.ScanResultDTO;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
@RequiredArgsConstructor
public class BatchScanManager {

    private final ScannerService scannerService;

    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public List<ScanResultDTO> process(ListRequestDTO request) {
        List<ScanRequestDTO> requests = request.dtos();

        List<Future<ScanResultDTO>> futures = new ArrayList<>();
        for (ScanRequestDTO req : requests) {
            futures.add(executor.submit(() -> scannerService.scan(req)));
        }

        List<ScanResultDTO> results = new ArrayList<>();
        for (Future<ScanResultDTO> future : futures) {
            try {
                results.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("Exception while scanning", e);
            }
        }

        return results;
    }
}

