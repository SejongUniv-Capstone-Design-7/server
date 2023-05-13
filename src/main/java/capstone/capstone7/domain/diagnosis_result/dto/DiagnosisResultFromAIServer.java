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

/**
 * errnum
 * 1이 in_info랑 out_info가 같을때 확률이 높아서 제대로 예측했다
 * 2는 in_info랑 out_info가 같은데 확률이 낮아서 아닐 확률이 높다
 * 3은 in_info랑 out_info가 다른데 한가지 클래스의 확률이 높다
 * 4는 in_info랑 out_info가 다른데 클래스들의 확률이 낮다
 */

/** class_prob_list
 * 0 고추 - 정상
 * 1 고추 - 고추마일드모틀바이러스병
 * 2 고추 - 고추점무늬병
 * 3 딸기 - 정상
 * 4 딸기 - 딸기잿빛곰팡이병
 * 5 딸기 - 딸기흰가루병
 * 6 상추 - 정상
 * 7 상추 - 상추균핵병
 * 8 상추 - 상추노균병
 * 9 토마토 - 정상
 * 10 토마토 - 토마토잎공팡이병
 * 11 토마토 - 토마토황화잎말이바이러스병
 */