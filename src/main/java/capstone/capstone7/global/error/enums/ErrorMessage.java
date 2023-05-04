package capstone.capstone7.global.error.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorMessage {
    /**
     * 성공
     */
    SUCCESS(200, true,  "요청에 성공하였습니다."),

    /**
     * 실패
     */
    INTERVAL_SERVER_ERROR(500, false,"요청을 처리하는 과정에서 서버가 예상하지 못한 오류가 발생하였습니다."),


    // Auth
    WRONG_PASSWORD(601, false, "비밀번호가 맞지 않습니다."),
    UNAUTHORIZED(602, false, "인증되지 않은 유저입니다."),
    FORBIDDEN(603, false, "권한이 없는 유저입니다."),

    // Member
    WRONG_REGION(701, false, "올바르지 않은 지역명입니다."),
    WRONG_EMAIL(702, false, "올바르지 않은 이메일입니다."),
    NOT_EXIST_USER(703, false, "해당 유저를 찾을 수 없습니다."),
    DUPLICATED_USER(704, false, "이미 해당 이메일을 가진 유저가 있습니다."),
    INVALID_USER(705, false, "path variable로 조회한 유저와 로그인한 유저가 다릅니다."),

    // Token
    EMPTY_TOKEN(801, false, "accessToken이 없습니다."),
    WRONG_JWT_SIGNITURE(802, false, "잘못된 JWT 서명입니다."),
    EXPIRED_JWT_TOKEN(803, false, "만료된 JWT 토큰입니다."),
    NOT_APPLY_JWT_TOKEN(804, false, "지원되지 않는 JWT 토큰입니다."),
    WRONG_JWT_TOKEN(805, false, "JWT 토큰이 잘못되었습니다."),

    // File
    CANNOT_UPLOAD(901, false, "파일을 업로드하지 못했습니다."),
    FILE_SIZE_EXCEEDED(902, false, "파일 사이즈가 10MB이하여야 합니다."),

    // Board
    NOT_EXIST_BOARD(1001, false, "해당 게시글이 존재하지 않습니다."),

    // Commnet
    NOT_EXIST_COMMENT(1101, false, "해당 댓글이 존재하지 않습니다."),
    NOT_EXIST_PARENT_COMMENT(1101, false, "해당 부모 댓글이 존재하지 않습니다."),

    // Like
    ALREADY_EXIST_BOARDLIKE(1201, false, "이미 해당 게시물에 좋아요를 눌렀습니다."),
    ALREADY_DELETED_BOARDLIKE(1202, false, "이미 해당 게시물에 좋아요 취소를 눌렀습니다."),

    // Send Data to AI Server
    FAIL_TO_SEND_AISERVER(1401, false, "AI 서버로 데이터를 전송하는 데 실패하였습니다."),
    EMPTY_FILE(1402, false, "이미지 파일을 첨부하지 않았습니다.");

    private final int code;
    private final boolean isSuccess;
    private final String message;

    ErrorMessage(int code, boolean isSuccess, String message) {
        this.code = code;
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
