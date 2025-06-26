package org.example.core.services;

import lombok.RequiredArgsConstructor;
import org.example.dto.ScanRequestDTO;
import org.example.dto.ValidationErrorDTO;
import org.example.core.validations.InputValidation;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ValidationService {
    private final List<InputValidation> validators;

    public List<ValidationErrorDTO> validate(ScanRequestDTO dto) {
        List<ValidationErrorDTO> errors = new ArrayList<>();

        for (InputValidation validator : validators) {
            errors.addAll(validator.validate(dto));
        }

        return errors;
    }
}

