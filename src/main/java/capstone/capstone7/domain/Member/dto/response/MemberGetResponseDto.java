package capstone.capstone7.domain.Member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberGetResponseDto {
    private Long memberId;

    private String email;
    private String nickname;

    private String region;
}
