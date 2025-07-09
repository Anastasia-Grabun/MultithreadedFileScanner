package org.example.core.validations.filters;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.example.core.validations.ValidationErrorFactory;
import org.example.dto.SearchParams;
import org.example.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ContentFilterValidation implements FilterValidation {

    private final ValidationErrorFactory errorFactory;

    @Override
    public boolean isApplicable(SearchParams params) {
        return params.contentContains() != null;
    }

    @Override
    public List<ValidationErrorDTO> validate(SearchParams params) {
        List<ValidationErrorDTO> errors = new ArrayList<>();
        String namePattern = params.namePattern();

        if (namePattern == null || !namePattern.matches(".*\\.txt$")) {
            errors.add(errorFactory.buildError("ERROR_CODE_7"));
        }

        return errors;
    }

}
