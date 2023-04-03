package capstone.capstone7.global.error.exception;

import capstone.capstone7.global.common.response.BaseResponseDto;
import capstone.capstone7.global.error.enums.ErrorMessage;
import capstone.capstone7.global.error.exception.custom.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public BaseResponseDto<ErrorMessage> businessExceptionHandle(BusinessException e) {
        log.warn("businessException : {}", e);
        return new BaseResponseDto(e.getErrorMessage());
    }



}
