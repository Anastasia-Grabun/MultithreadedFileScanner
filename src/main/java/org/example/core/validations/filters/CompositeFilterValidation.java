package org.example.core.validations.filters;

import lombok.RequiredArgsConstructor;
import org.example.dto.ScanRequestDTO;
import org.example.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompositeFilterValidation {

    private final List<FilterValidation> validators;

    public List<ValidationErrorDTO> validateAll(ScanRequestDTO requestDTO) {
        return validators.stream()
                .filter(v -> v.isApplicable(requestDTO))
                .flatMap(v -> v.validate(requestDTO).stream())
                .collect(Collectors.toList());
    }

}
