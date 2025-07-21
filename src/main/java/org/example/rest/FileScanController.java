package org.example.rest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.example.core.security.UserContext;
import org.example.core.services.BatchScanManager;
import org.example.core.services.SearchTaskRegistry;
import org.example.dto.ListRequestDTO;
import org.example.dto.ScanResultDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("api/v1/scan")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class FileScanController {

    private final BatchScanManager batchScanManager;
    private final SearchTaskRegistry taskRegistry;
    private final UserContext userContext;

    @PostMapping()
    public List<ScanResultDTO> scanBatch(@RequestBody ListRequestDTO request) {
        return batchScanManager.process(request);
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelUserTasks() {
        String username = userContext.getCurrentUsername();
        taskRegistry.cancelAll(username);
        
        return ResponseEntity.ok("All tasks for user '" + username + "' have been cancelled.");
    }

}
