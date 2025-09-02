package com.tl.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class PerformanceInfoDto {
	private String per_title; // 공연 제목
	private String per_startD; // 공연 시작일
	private String per_endD; // 공연 종료일
	private String per_peace; // 공연 장소
	private String per_runT; // 공연 런타임
	private String per_sche; // 공연 일정
	private String per_genre; // 공연 장르
	private String per_poster; // 공연 포스터 이미지
	private Map<String, String> per_ticket = new HashMap<>();// 공연 예매 링크

	public PerformanceInfoDto(String title, String startD, String endD, String peace, String runT, String sche,
			String genre, String poster, ArrayList<RelateDto> relate) {
		per_title = (title != null) ? title : "";
		per_startD = (startD != null) ? startD : "";
		per_endD = (endD != null) ? endD : "";
		per_peace = (peace != null) ? peace : "";
		per_runT = (runT != null) ? runT : "";
		per_sche = (sche != null) ? sche : "";
		per_genre = (genre != null) ? genre : "";
		per_poster = (poster != null) ? poster : "";

		if (relate != null) {
			for (RelateDto rel : relate) {
				if (rel.getRelatenm() != null && rel.getRelateurl() != null) {
					per_ticket.put(rel.getRelatenm(), rel.getRelateurl());
				}
			}
		}
	}
}
