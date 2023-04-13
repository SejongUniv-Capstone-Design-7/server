package capstone.capstone7.domain.Member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberPatchResponseDto {
    private String nickname;
    private String region;
}
