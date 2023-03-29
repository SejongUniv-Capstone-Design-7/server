package capstone.capstone7.domain.Member.entity;

import capstone.capstone7.domain.Member.entity.enums.Region;
import capstone.capstone7.global.common.entity.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Member extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String nickname;
    private Region region;

    @Builder
    public Member(String email, String password, String nickname, Region region) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.region = region;
    }
}
