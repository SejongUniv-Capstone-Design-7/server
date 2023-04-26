package capstone.capstone7.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MemberPatchRequestDto {
    @Pattern(regexp = "^[a-zA-Zㄱ-ㅎ가-힣0-9]+$",
            message = "닉네임는 공백없이 한글, 영어, 숫자만 사용가능합니다.")
    @Size(min=4, max=12, message = "4자 ~ 12자의 닉네임이어야 합니다.")
    private String nickname;

    private String region;
}
