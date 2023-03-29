package capstone.capstone7.global.error.exception.custom;

import capstone.capstone7.global.error.enums.ErrorMessage;
import lombok.Getter;

@Getter
public class AuthException extends BusinessException {
    public AuthException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
