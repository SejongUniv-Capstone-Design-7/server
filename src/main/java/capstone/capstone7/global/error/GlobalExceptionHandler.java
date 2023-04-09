package capstone.capstone7.global.error;

import capstone.capstone7.global.common.response.BaseResponseDto;
import capstone.capstone7.global.error.enums.ErrorMessage;
import capstone.capstone7.global.error.exception.custom.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static capstone.capstone7.global.error.enums.ErrorMessage.FILE_SIZE_EXCEEDED;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public BaseResponseDto<ErrorMessage> businessExceptionHandle(BusinessException e) {
        log.warn("businessException : {}", e);
        return new BaseResponseDto(e.getErrorMessage());
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    public BaseResponseDto<ErrorMessage> fileSizeLimitExceededExceptionHandle(FileSizeLimitExceededException e){
        log.warn("businessException : {}", e);
        return new BaseResponseDto(FILE_SIZE_EXCEEDED);
    }


}
