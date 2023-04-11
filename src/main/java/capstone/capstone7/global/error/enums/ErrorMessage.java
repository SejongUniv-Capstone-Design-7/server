package capstone.capstone7.global.error.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

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
    CANNOT_FIND_USER(BAD_REQUEST, false, "해당 유저를 찾을 수 없습니다."),
    WRONG_PASSWORD(BAD_REQUEST, false, "비밀번호가 맞지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, false, "인증되지 않은 유저입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, false, "권한이 없는 유저입니다."),
    EMPTY_TOKEN(BAD_REQUEST, false, "accessToken이 없습니다."),
    // Token
    WRONG_JWT_SIGNITURE(BAD_REQUEST, false, "잘못된 JWT 서명입니다."),
    EXPIRED_JWT_TOKEN(BAD_REQUEST, false, "만료된 JWT 토큰입니다."),
    NOT_APPLY_JWT_TOKEN(BAD_REQUEST, false, "지원되지 않는 JWT 토큰입니다."),
    WRONG_JWT_TOKEN(BAD_REQUEST, false, "JWT 토큰이 잘못되었습니다."),
    // File
    CANNOT_UPLOAD(BAD_REQUEST, false, "파일을 업로드하지 못했습니다."),
    FILE_SIZE_EXCEEDED(BAD_REQUEST, false, "파일 사이즈가 10MB이하여야 합니다."),

    // Board
    NOT_EXIST_BOARD(NOT_FOUND, false, "해당 게시글이 존재하지 않습니다.");

    private final int code;
    private final boolean isSuccess;
    private final String message;

    ErrorMessage(HttpStatus code, boolean isSuccess, String message) {
        this.code = code.value();
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
