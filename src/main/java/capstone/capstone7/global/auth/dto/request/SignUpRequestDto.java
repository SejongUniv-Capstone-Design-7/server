package capstone.capstone7.global.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequestDto {
    @Email(message="이메일 형식에 맞지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String email;

    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,12}",
            message = "비밀번호는 영문과 숫자가 적어도 1개 이상씩 포함된 8자 ~ 12자의 비밀번호여야 합니다.")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    @Pattern(regexp = "^[a-zA-Zㄱ-ㅎ가-힣0-9]+$",
            message = "닉네임는 공백없이 한글, 영어, 숫자만 사용가능합니다.")
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Size(min=4, max=12, message = "4자 ~ 12자의 닉네임이어야 합니다.")
    private String nickname;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private String region;
}
