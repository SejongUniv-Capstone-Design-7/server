package capstone.capstone7.global.auth.dto.request;

import lombok.Getter;

@Getter
public class SignUpRequestDto {
    private String email;
    private String password;
    private String nickname;
    private String region;
}
