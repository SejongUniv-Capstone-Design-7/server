package capstone.capstone7.global.error.exception.custom;

import capstone.capstone7.global.error.enums.ErrorMessage;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final ErrorMessage errorMessage;

    public BusinessException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}
