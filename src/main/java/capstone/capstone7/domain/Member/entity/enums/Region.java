package capstone.capstone7.domain.Member.entity.enums;

public enum Region {

    SEOUL("서울특별시"),
    INCHEON("인천광역시"),
    DAEJEON("대전광역시"),
    GWANGJU("광주광역시"),
    DAEGU("대구광역시"),
    ULSAN("울산광역시"),
    BUSAN("부산광역시"),
    SEJONG("세종특별자치시"),
    GYEONGGI_DO("경기도"),
    GANGWON_DO("강원도"),
    CHUNGCHEONGBUK_DO("충청북도"),
    CHUNGCHEONGNAM_DO("충청남도"),
    JEOLLABUK_DO("전라북도"),
    JEOLLANAM_DO("전라남도"),
    GYEONGSANGBUK_DO("경상북도"),
    GYEONGSANGNAM_DO("경상남도"),
    JEJU("제주특별자치도");


    private final String regionKoreanName;

    Region(String regionKoreanName){
        this.regionKoreanName = regionKoreanName;
    }

    public String getKoreanName(){
        return regionKoreanName;
    }

    public static Region getRegionEnumByKrName(String regionKrName){
        for(Region region : Region.values()){
            if(region.getKoreanName().equals(regionKrName)){
                return region;
            }
        }
        return null;
    }
}


