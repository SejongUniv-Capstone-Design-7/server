package capstone.capstone7.global.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @Email(message="이메일 형식에 맞지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,12}",
            message = "비밀번호는 영문과 숫자가 적어도 1개 이상씩 포함된 8자 ~ 12자의 비밀번호여야 합니다.")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;
}
