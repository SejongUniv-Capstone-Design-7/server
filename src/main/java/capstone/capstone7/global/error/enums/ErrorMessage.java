package capstone.capstone7.global.error.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorMessage {
    /**
     * 성공
     */
    SUCCESS(OK, true,  "요청에 성공하였습니다."),

    /**
     * 실패
     */
    INTERVAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, false,"요청을 처리하는 과정에서 서버가 예상하지 못한 오류가 발생하였습니다."),


    // Auth
    WRONG_REGION(BAD_REQUEST, false, "올바르지 않은 지역명입니다."),
    WRONG_EMAIL(BAD_REQUEST, false, "올바르지 않은 이메일입니다."),


    // File
    CANNOT_UPLOAD(BAD_REQUEST, false, "파일을 업로드하지 못했습니다.");

    private final int code;
    private final boolean isSuccess;
    private final String message;

    ErrorMessage(HttpStatus code, boolean isSuccess, String message) {
        this.code = code.value();
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
