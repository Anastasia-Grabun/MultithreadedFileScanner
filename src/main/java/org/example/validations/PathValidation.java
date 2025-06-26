package org.example.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.example.dto.ScanRequestDTO;
import org.example.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class PathValidation implements InputValidation {

    private final ValidationErrorFactory errorFactory;

    @Override
    public List<ValidationErrorDTO> validate(ScanRequestDTO requestDTO) {
        String path = requestDTO.path();
        List<ValidationErrorDTO> errors = new ArrayList<>();

        if (path == null || path.isEmpty()) {
            errors.add(errorFactory.buildError("ERROR_CODE_1"));
        }

        return errors;
    }

}

