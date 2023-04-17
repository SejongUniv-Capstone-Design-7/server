package capstone.capstone7.domain.member.entity;

import capstone.capstone7.domain.member.entity.enums.Region;
import capstone.capstone7.global.auth.entity.RefreshToken;
import capstone.capstone7.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Member extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Region region;

    @OneToOne(fetch = FetchType.LAZY)
    private RefreshToken refreshToken;

    @Builder
    public Member(String email, String password, String nickname, Region region) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.region = region;
    }

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
    }

    public void update(String nickname, String region){
        if(nickname != null){
            this.nickname = nickname;
        }
        if(region != null){
            this.region = Region.getRegionEnumByKrName(region);
        }
    }
}
