package capstone.capstone7.global.auth.dto;

import capstone.capstone7.global.auth.entity.TokenInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String grantType; // Bearer
    private String accessToken;
    private String refreshToken;
    private Long memberId;

    public LoginResponseDto(TokenInfo tokenInfo, Long memberId){
        this.grantType = tokenInfo.getGrantType();
        this.accessToken = tokenInfo.getAccessToken();
        this.refreshToken = tokenInfo.getRefreshToken();
        this.memberId = memberId;
    }
}
