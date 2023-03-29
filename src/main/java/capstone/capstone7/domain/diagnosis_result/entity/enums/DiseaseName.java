package capstone.capstone7.domain.diagnosis_result.entity.enums;

public enum DiseaseName {
    PEPPER_MILD_MOTTLE_VIRUS("고추마일드모틀바이러스"),
    PEPPER_BACTERIAL_SPOT("고추점무늬병"),
    STRAWBERRY_GRAY_MOLD("딸기잿빛곰팡이병"),
    STRAWBERRY_POWDERY_MILDEW("딸기흰가루병"),
    RETTUCE_SCLEROTINIA_ROT("상추균핵병"),
    RETTUCE_DOWNY_MILDEW("상추노균병"),
    TOMATO_LEAF_MOLD("토마토잎곰팡이병"),
    TOMATO_YELLOW_LEAF_CURL_VIRUS("토마토황화잎말이바이러스");


    private final String koreanDiseaseName;
    DiseaseName(String koreanDiseaseName){
        this.koreanDiseaseName = koreanDiseaseName;
    }

    public String getKoreanDiseaseName() {
        return koreanDiseaseName;
    }
}
