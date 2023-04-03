package capstone.capstone7.global.error.exception.custom;

import capstone.capstone7.global.error.enums.ErrorMessage;

public class InvalidTokenException extends BusinessException{
    public InvalidTokenException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
