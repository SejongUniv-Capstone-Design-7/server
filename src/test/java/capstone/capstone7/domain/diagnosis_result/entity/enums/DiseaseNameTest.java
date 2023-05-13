package capstone.capstone7.domain.diagnosis_result.entity.enums;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiseaseNameTest {

    @DisplayName("한국어 병해충명으로 Enum타입 객체 얻기")
    @Test
    void DiseaseName_Of_Test() throws Exception{
        //given
        String koreanDiseaseName = "토마토황화잎말이바이러스";

        //when
        DiseaseName diseaseEnum = DiseaseName.of(koreanDiseaseName);

        //then
        // 근데 이렇게 Enum 비교를 하는게 맞나? EqualTo 주소값 비교인가?
        Assertions.assertThat(diseaseEnum).isEqualTo(DiseaseName.TOMATO_YELLOW_LEAF_CURL_VIRUS);
    }
}