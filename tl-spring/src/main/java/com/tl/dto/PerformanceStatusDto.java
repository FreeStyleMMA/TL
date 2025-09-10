package com.tl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PerformanceStatusDto { // 공연 예매현황판을 받아올 클래스
	private String prfplcnm;	// 공연장 이름
	private String seatcnt;		// 좌석수
	private String rnum;		// 순위
	private String poster;		// 포스터 이미지
	private String prfpd;		// 공연기간
	private String mt20id;		// 공연ID
	private String prfnm;		// 공연명
	private String cate;		// 장르
	private String prfdtcnt;	// 상연 회수
	private String area;		// 지역
}
