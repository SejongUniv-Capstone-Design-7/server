package capstone.capstone7.domain.diagnosis_result.dto;

import capstone.capstone7.domain.member.entity.enums.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class DiagnosisResultMonthlyCountDto {
    private String region;
    private Long diseaseCount;

    public void updateCount(Long diseaseCount) {
        this.diseaseCount = diseaseCount;
    }
}