package org.example.core.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.core.security.UserContext;
import org.example.dto.ListRequestDTO;
import org.example.dto.ScanRequestDTO;
import org.example.dto.ScanResultDTO;
import org.example.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchScanManager {

    private final ScannerService scannerService;
    private final UserContext userContext;
    private final SearchTaskRegistry taskRegistry;
    private final ExecutorService executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().factory());

    public List<ScanResultDTO> process(ListRequestDTO request) {
        List<ScanRequestDTO> requests = request.dtos();
        List<Future<ScanResultDTO>> futures = new ArrayList<>();
        String username = userContext.getCurrentUsername();

        for (ScanRequestDTO req : requests) {
            Future<ScanResultDTO> future = executor.submit(() -> scannerService.scan(req));
            taskRegistry.register(username, future);
            futures.add(future);
        }

        List<ScanResultDTO> results = new ArrayList<>();
        for (Future<ScanResultDTO> future : futures) {
            try {
                results.add(future.get());
            } catch (CancellationException e) {
                log.info("Task was cancelled by user");
                results.add(ScanResultDTO.withErrors(
                        List.of(new ValidationErrorDTO("CANCELLED", "Task was cancelled"))));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.warn("Thread was interrupted");
                throw new RuntimeException("Interrupted while waiting for scan result", e);
            } catch (ExecutionException e) {
                log.error("Exception while task was executing", e);
                throw new RuntimeException("Execution error during scan", e);
            }
        }

        return results;
    }

}

