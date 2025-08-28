package com.tl.dto;

import lombok.Data;

@Data
public class PerformanceDto {
	public String mt20id;     // 공연 ID (예: PF178134)
	public String prfnm;      // 공연명 (예: 반짝반짝 인어공주)
	public String genrenm;    // 공연 장르명 (예: 뮤지컬)
	public String prfstate;   // 공연 상태 (예: 공연중, 공연완료)
	public String prfpdfrom;  // 공연 시작일 (예: 20210821)
	public String prfpdto;    // 공연 종료일 (예: 20240929)
	public String poster;     // 포스터 이미지 경로(URL) (예: http://www.kopis.or.kr/upload/pfmPoster/PF_PF178134_210809_125033.PNG)
	public String fcltynm;    // 공연 시설명 / 공연장명 (예: 달밤엔씨어터)
	public String openrun;    // 오픈런 여부 (Y/N) (예: Y)
	public String area;       // 공연 지역 (예: 서울특별시)

}
