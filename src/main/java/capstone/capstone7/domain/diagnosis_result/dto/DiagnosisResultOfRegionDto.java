package capstone.capstone7.domain.diagnosis_result.dto;

import capstone.capstone7.domain.diagnosis_result.entity.enums.DiseaseName;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DiagnosisResultOfRegionDto {
    private String diseaseName;
    private Long diseaseCount;

    public void updateCount(Long diseaseCount) {
        this.diseaseCount = diseaseCount;
    }
}
