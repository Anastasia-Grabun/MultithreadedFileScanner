package org.example.core.services;

import lombok.RequiredArgsConstructor;
import org.example.core.validations.filters.FilterValidation;
import org.example.dto.ScanRequestDTO;
import org.example.dto.ValidationErrorDTO;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilterValidationService {

    private final List<FilterValidation> validators;

    public List<ValidationErrorDTO> validate(ScanRequestDTO request) {
        if (request.searchParams() == null) {
            return Collections.emptyList();
        }

        return validators.stream()
                .filter(v -> v.isApplicable(request))
                .flatMap(v -> v.validate(request).stream())
                .collect(Collectors.toList());
    }

}
