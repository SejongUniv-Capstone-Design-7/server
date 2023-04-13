package capstone.capstone7.domain.Member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberGetResponseDto {
    private Long id;

    private String email;
    private String nickname;

    private String region;
}
