package org.example.rest;

import lombok.RequiredArgsConstructor;
import org.example.core.services.BatchScanManager;
import org.example.dto.ListRequestDTO;
import org.example.dto.ScanBatchForm;
import org.example.dto.ScanForm;
import org.example.dto.ScanRequestDTO;
import org.example.dto.ScanResultDTO;
import org.example.dto.SearchParams;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final BatchScanManager batchScanManager;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String index(Model model) {
        ScanBatchForm batchForm = new ScanBatchForm();

        batchForm.setTasks(List.of(
                new ScanForm("D:/example/path", "*.txt", null, null, null, null),
                new ScanForm("/home/dev/src", "*.xml", null, null, null, null),
                new ScanForm("/home/dev/resources", "*.properties", "server.port", null, null, null)
        ));

        model.addAttribute("scanBatchForm", batchForm);
        return "index";
    }

    @PostMapping("/scan")
    public String scan(@ModelAttribute ScanBatchForm batchForm, Model model) {
        List<ScanRequestDTO> scanRequests = batchForm.getTasks().stream().map(scanForm -> {
            LocalDate modifiedAfter = scanForm.getModifiedAfter();

            SearchParams searchParams = new SearchParams(
                    scanForm.getMinSize(),
                    scanForm.getMaxSize(),
                    modifiedAfter,
                    scanForm.getContentContains()
            );

            return new ScanRequestDTO(
                    scanForm.getPath(),
                    scanForm.getMask(),
                    searchParams
            );
        }).toList();

        ListRequestDTO listRequest = new ListRequestDTO(scanRequests);
        List<ScanResultDTO> results = batchScanManager.process(listRequest);

        model.addAttribute("results", results);

        return "result";
    }


    @PostMapping("/cancel")
    public String cancel(Model model) {
        // TODO: Добавьте логику cancel, если нужна
        model.addAttribute("scanBatchForm", new ScanBatchForm());
        return "index";
    }

}
