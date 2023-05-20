package capstone.capstone7.domain.diagnosis_result.entity.enums;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum DiseaseName {
    PEPPER_MILD_MOTTLE_VIRUS("고추마일드모틀바이러스", 1),
    PEPPER_BACTERIAL_SPOT("고추점무늬병", 2),
    STRAWBERRY_GRAY_MOLD("딸기잿빛곰팡이병", 4),
    STRAWBERRY_POWDERY_MILDEW("딸기흰가루병", 5),
    LETTUCE_SCLEROTINIA_ROT("상추균핵병", 7),
    LETTUCE_DOWNY_MILDEW("상추노균병", 8),
    TOMATO_LEAF_MOLD("토마토잎곰팡이병",10),
    TOMATO_YELLOW_LEAF_CURL_VIRUS("토마토황화잎말이바이러스", 11);


    private final String koreanDiseaseName;
    private final Integer idx;
    DiseaseName(String koreanDiseaseName, Integer idx){
        this.koreanDiseaseName = koreanDiseaseName;
        this.idx = idx;
    }

    public String getKoreanDiseaseName() {
        return koreanDiseaseName;
    }
    public Integer getIdxOfDiseaseName(){
        return idx;
    }

    // 한국어 병해충 이름으로 DiseaseName Enum 객체 찾기
    private static final Map<String, String> KRNAME_MAP = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(DiseaseName::getKoreanDiseaseName, DiseaseName::name)));

    public static DiseaseName getEnumByKrDiseaseName(final String koreanDiseaseName){
        return DiseaseName.valueOf(KRNAME_MAP.get(koreanDiseaseName));
    }

    // 병해충 idx로 DiseaseName Enum 객체 찾기
    private static final Map<Integer, String> IDX_MAP = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(DiseaseName::getIdxOfDiseaseName, DiseaseName::name)));

    public static DiseaseName getEnumByIdx(final int idx){
        return DiseaseName.valueOf(IDX_MAP.get(idx));
    }
}

/**
 * AI 서버에서 받는 class_prob_list의 인덱스별 결과 정보
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
