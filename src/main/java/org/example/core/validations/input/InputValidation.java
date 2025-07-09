package org.example.core.validations.input;

import org.example.dto.ScanRequestDTO;
import org.example.dto.ValidationErrorDTO;
import java.util.List;

public interface InputValidation {

    List<ValidationErrorDTO> validate(ScanRequestDTO requestDTO);

}
