package org.example.core.validations.filters;

import lombok.RequiredArgsConstructor;
import org.example.dto.SearchParams;
import org.example.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompositeFilterValidation {

    private final List<FilterValidation> validators;

    public List<ValidationErrorDTO> validateAll(SearchParams params) {
        if (params == null) {
            return List.of();
        }

        return validators.stream()
                .filter(v -> v.isApplicable(params))
                .flatMap(v -> v.validate(params).stream())
                .collect(Collectors.toList());
    }

}
