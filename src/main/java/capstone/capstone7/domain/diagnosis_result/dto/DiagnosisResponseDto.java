package capstone.capstone7.domain.diagnosis_result.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DiagnosisResponseDto {
    private Integer result;
    private String input_crop;
    private String output_crop;

}
