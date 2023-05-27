package capstone.capstone7.domain.diagnosis_result.dto;

import capstone.capstone7.domain.diagnosis_result.entity.enums.DiseaseName;
import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class DiagnosisResultOfRegion {
    private DiseaseName diseaseName;
    private Long diseaseCount;
}

