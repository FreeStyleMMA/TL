package com.tl.util;

	import java.util.HashMap;
	import java.util.Map;

	public class CodeMapper {
	    public static final Map<String, String> REGION_MAP = new HashMap<>();
	    public static final Map<String, String> GENRE_MAP = new HashMap<>();

	    static {
	        // 지역 코드 매핑
	        REGION_MAP.put("서울특별시", "11");
	        REGION_MAP.put("부산광역시", "26");
	        REGION_MAP.put("대구광역시", "27");
	        REGION_MAP.put("인천광역시", "28");
	        REGION_MAP.put("광주광역시", "29");
	        REGION_MAP.put("대전광역시", "30");
	        REGION_MAP.put("울산광역시", "31");
	        REGION_MAP.put("세종특별자치시", "36");
	        REGION_MAP.put("경기도", "41");
	        REGION_MAP.put("강원특별자치도", "51");
	        REGION_MAP.put("충청북도", "43");
	        REGION_MAP.put("충청남도", "44");
	        REGION_MAP.put("전라북도", "45");
	        REGION_MAP.put("전라남도", "46");
	        REGION_MAP.put("경상북도", "47");
	        REGION_MAP.put("경상남도", "48");
	        REGION_MAP.put("제주특별자치도", "50");

	        // 장르 코드 매핑
	        GENRE_MAP.put("연극", "AAAA");
	        GENRE_MAP.put("무용(서양/한국무용)", "BBBC");
	        GENRE_MAP.put("대중무용", "BBBE");
	        GENRE_MAP.put("서양음악(클래식)", "CCCA");
	        GENRE_MAP.put("한국음악(국악)", "CCCC");
	        GENRE_MAP.put("대중음악", "CCCD");
	        GENRE_MAP.put("복합", "EEEA");
	        GENRE_MAP.put("서커스/마술", "EEEB");
	        GENRE_MAP.put("뮤지컬", "GGGA");
	    }

	    public static String getRegionCode(String regionName) {
	        return REGION_MAP.getOrDefault(regionName, null); // 없는 경우 null 반환
	    }

	    public static String getGenreCode(String genreName) {
	        return GENRE_MAP.getOrDefault(genreName, null); // 없는 경우 null 반환
	    }
}
