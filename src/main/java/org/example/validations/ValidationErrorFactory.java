package org.example.validations;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.example.dto.ValidationErrorDTO;
import org.example.util.ErrorCodeUtil;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ValidationErrorFactory {

    private final ErrorCodeUtil messageProvider;

    public ValidationErrorDTO buildError(String errorCode){
        String description = messageProvider.getErrorDescription(errorCode);
        return new ValidationErrorDTO(errorCode, description);
    }

}
