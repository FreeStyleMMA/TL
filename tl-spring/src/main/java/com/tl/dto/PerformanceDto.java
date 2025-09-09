package com.tl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PerformanceDto { // api에서 받아온 공연 목록의 정보를 받아올 클래스
	private String mt20id;     // 공연 ID (예: PF178134)
	private String prfnm;      // 공연명 (예: 반짝반짝 인어공주)
	private String genrenm;    // 공연 장르명 (예: 뮤지컬)
	private String prfstate;   // 공연 상태 (예: 공연중, 공연완료)
	private String prfpdfrom;  // 공연 시작일 (예: 20210821)
	private String prfpdto;    // 공연 종료일 (예: 20240929)
	private String poster;     // 포스터 이미지 경로(URL) (예: http://www.kopis.or.kr/upload/pfmPoster/PF_PF178134_210809_125033.PNG)
	private String fcltynm;    // 공연 시설명 / 공연장명 (예: 달밤엔씨어터)
	private String openrun;    // 오픈런 여부 (Y/N) (예: Y)
	private String area;       // 공연 지역 (예: 서울특별시)

}
