package org.example.core.validations.filters;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.example.core.validations.ValidationErrorFactory;
import org.example.dto.SearchParams;
import org.example.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DateFilterValidation implements FilterValidation {

    private final ValidationErrorFactory errorFactory;

    @Override
    public boolean isApplicable(SearchParams params) {
        return params.modifiedAfter() != null;
    }

    @Override
    public List<ValidationErrorDTO> validate(SearchParams params) {
        List<ValidationErrorDTO> errors = new ArrayList<>();
        LocalDate modifiedAfter = params.modifiedAfter();

        if (modifiedAfter != null) {
            validateDate(modifiedAfter, errors);
        }

        return errors;
    }

    private void validateDate(LocalDate dateTime, List<ValidationErrorDTO> errors) {
        checkFutureDate(dateTime, errors);
        checkAncientDate(dateTime, errors);
    }

    private void checkFutureDate(LocalDate dateTime, List<ValidationErrorDTO> errors) {
        if (dateTime.isAfter(LocalDate.now())) {
            errors.add(errorFactory.buildError("ERROR_CODE_5"));
        }
    }

    private void checkAncientDate(LocalDate dateTime, List<ValidationErrorDTO> errors) {
        if (dateTime.isBefore(LocalDate.of(1970, 1, 1))) {
            errors.add(errorFactory.buildError("ERROR_CODE_6"));
        }
    }

}

