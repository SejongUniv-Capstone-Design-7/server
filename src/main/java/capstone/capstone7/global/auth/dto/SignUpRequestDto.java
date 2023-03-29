package capstone.capstone7.global.auth.dto;

import capstone.capstone7.domain.Member.entity.enums.Region;
import lombok.Getter;

@Getter
public class SignUpRequestDto {
    private String email;
    private String password;
    private String nickname;
    private String region;
}
