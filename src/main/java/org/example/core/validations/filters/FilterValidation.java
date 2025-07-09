package org.example.core.validations.filters;

import org.example.dto.SearchParams;
import org.example.dto.ValidationErrorDTO;
import java.util.List;

public interface FilterValidation {

    boolean isApplicable(SearchParams params);

    List<ValidationErrorDTO> validate(SearchParams params);

}
