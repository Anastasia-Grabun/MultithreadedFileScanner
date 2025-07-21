package org.example.core.validations.filters;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.example.core.validations.ValidationErrorFactory;
import org.example.dto.ScanRequestDTO;
import org.example.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ContentFilterValidation implements FilterValidation {

    private final ValidationErrorFactory errorFactory;

    @Override
    public boolean isApplicable(ScanRequestDTO request) {
        return request.searchParams() != null &&
                request.searchParams().contentContains() != null;
    }

    @Override
    public List<ValidationErrorDTO> validate(ScanRequestDTO request) {
        List<ValidationErrorDTO> errors = new ArrayList<>();

        String mask = request.mask();
        if (mask == null || !mask.endsWith(".txt")) {
            errors.add(errorFactory.buildError("ERROR_CODE_7"));
        }

        return errors;
    }

}

