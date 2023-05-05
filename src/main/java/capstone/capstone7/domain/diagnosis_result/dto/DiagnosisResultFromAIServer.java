package capstone.capstone7.domain.diagnosis_result.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class DiagnosisResultFromAIServer {
    private Integer errnum;
    private String disease_name;
    private String in_info;
    private String out_info;
    private List<Float> class_prob_list;
}
