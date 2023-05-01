package capstone.capstone7.domain.diagnosis_result.dto;

public class Diagnosis {
    private String label1;
    private Double probability1;
    private String label2;
    private Double probability2;
    private String label3;
    private Double probability3;
}
/**
 * json 예시
 * {
 *  "result": "0", // result 타입 : 0,1,2,3
 *  "input_crop": "tomato", //WAS로부터 받은 작물이름
 *  "output_crop": "tomato", //진단결과로 나온 작물
 *  "diagnosis": {
 *  "label1": "Disease A",
 *  "probability1": 0.75,
 *  "label2": "Disease B",
 *  "probability2": 0.2,
 *  "label3: "Normal",
 *  "probability3": 0.05
 *  }
 * }
 */