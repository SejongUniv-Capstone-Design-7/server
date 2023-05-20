package capstone.capstone7.domain.diagnosis_result.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DiagnosisResponseDto {
    private Integer errnum;
    private Boolean isCorrect;
    private String diseaseName;
    private String inCropInfo;
    private String outCropInfo;
    private List<Float> classProbabilityList;
    private String errorMessage;

}
