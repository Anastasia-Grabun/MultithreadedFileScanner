package org.example.core.validations.filters;

import org.example.dto.ScanRequestDTO;
import org.example.dto.SearchParams;
import org.example.dto.ValidationErrorDTO;
import java.util.List;

public interface FilterValidation {

    boolean isApplicable(ScanRequestDTO params);

    List<ValidationErrorDTO> validate(ScanRequestDTO params);

}
