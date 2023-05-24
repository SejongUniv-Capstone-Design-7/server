package capstone.capstone7.domain.diagnosis_result.entity;

import capstone.capstone7.domain.diagnosis_result.entity.enums.DiseaseName;
import capstone.capstone7.domain.member.entity.Member;
import capstone.capstone7.domain.member.entity.enums.Region;
import capstone.capstone7.global.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class DiagnosisResult extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Region region;

    @Enumerated(EnumType.STRING)
    private DiseaseName diseaseName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public DiagnosisResult(DiseaseName diseaseName, Member member, Region region) {
        this.diseaseName = diseaseName;
        this.member = member;
        this.region = region;
    }
}
