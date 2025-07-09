package org.example.core.validations.filters;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.example.core.validations.ValidationErrorFactory;
import org.example.dto.ScanRequestDTO;
import org.example.dto.SearchParams;
import org.example.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class SizeFilterValidation implements FilterValidation {

    private final ValidationErrorFactory errorFactory;

    @Override
    public boolean isApplicable(ScanRequestDTO requestDTO) {
        SearchParams params = requestDTO.searchParams();
        return hasSizeParams(params);
    }

    @Override
    public List<ValidationErrorDTO> validate(ScanRequestDTO requestDTO) {
        SearchParams params = requestDTO.searchParams();
        List<ValidationErrorDTO> errors = new ArrayList<>();
        validateSize(params, errors);

        return errors;
    }

    private boolean hasSizeParams(SearchParams params) {
        return params.minSize() != null || params.maxSize() != null;
    }

    private void validateSize(SearchParams params, List<ValidationErrorDTO> errors) {
        validateMinSize(params.minSize(), errors);
        validateMaxSize(params.maxSize(), errors);
    }

    private void validateMinSize(Long minSize, List<ValidationErrorDTO> errors) {
        if (minSize != null && minSize < 0) {
            errors.add(errorFactory.buildError("ERROR_CODE_3"));
        }
    }

    private void validateMaxSize(Long maxSize, List<ValidationErrorDTO> errors) {
        if (maxSize != null && maxSize < 0) {
            errors.add(errorFactory.buildError("ERROR_CODE_4"));
        }
    }

}