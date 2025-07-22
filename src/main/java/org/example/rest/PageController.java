package org.example.rest;

import lombok.RequiredArgsConstructor;
import org.example.core.services.BatchScanManager;
import org.example.dto.ListRequestDTO;
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
import java.time.format.DateTimeParseException;
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
        model.addAttribute("scanForm", new ScanForm());
        return "index";
    }

    @PostMapping("/scan")
    public String scan(@ModelAttribute ScanForm scanForm, Model model) {
        LocalDate modifiedAfter = null;
        if (scanForm.getModifiedAfter() != null && !scanForm.getModifiedAfter().isBlank()) {
            try {
                modifiedAfter = LocalDate.parse(scanForm.getModifiedAfter());
            } catch (DateTimeParseException e) {
                model.addAttribute("error", "Invalid date format. Use yyyy-MM-dd.");
                model.addAttribute("scanForm", scanForm);
                return "index";
            }
        }

        SearchParams searchParams = new SearchParams(
                scanForm.getMinSize(),
                scanForm.getMaxSize(),
                modifiedAfter,
                scanForm.getContentContains()
        );


        ScanRequestDTO scanRequestDTO = new ScanRequestDTO(
                scanForm.getPath(),
                scanForm.getMask(),
                searchParams
        );

        ListRequestDTO listRequest = new ListRequestDTO(List.of(scanRequestDTO));
        List<ScanResultDTO> results = batchScanManager.process(listRequest);

        model.addAttribute("results", results);
        return "result";
    }

}
