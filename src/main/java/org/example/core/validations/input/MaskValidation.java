package org.example.core.validations.input;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.example.core.validations.ValidationErrorFactory;
import org.example.core.validations.input.InputValidation;
import org.example.dto.ScanRequestDTO;
import org.example.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class MaskValidation implements InputValidation {

    private final ValidationErrorFactory errorFactory;

    @Override
    public List<ValidationErrorDTO> validate(ScanRequestDTO requestDTO) {
        String mask = requestDTO.mask();
        List<ValidationErrorDTO> errors = new ArrayList<>();

        if (mask == null || mask.isEmpty()) {
            errors.add(errorFactory.buildError("ERROR_CODE_2"));
        }

        return errors;
    }

}

