package capstone.capstone7.domain.diagnosis_result.dto;

import java.util.List;

public class DiagnosisResultFromAIServer {
    private Integer errnum;
    private String disease_name;
    private String in_info;
    private String out_info;
    private List<Double> class_prob_list;
}
