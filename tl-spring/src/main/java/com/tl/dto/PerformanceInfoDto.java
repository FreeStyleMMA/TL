package com.tl.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PerformanceInfoDto {
	private String per_title; 		// 공연 제목
	private String per_startD; 		// 공연 시작일
	private String per_endD; 		// 공연 종료일
	private String per_runT; 		// 공연 런타임
	private String per_sche; 		// 공연 일정
	private String per_genre; 		// 공연 장르
	private String per_poster; 		// 공연 포스터 이미지
	private String per_peace; 		// 공연 장소
	private String per_address; 	// 공연 주소
	private String per_latitude;	// 공연 위도
	private String per_longitude;   // 공연 경도
	private String per_region;		// 공연 지역
	private String per_rank;		// 공연 랭킹
	private List<TicketDto> per_ticket = new ArrayList<TicketDto>();// 공연 티켓 예매처 정보

	//필드 데이터 값 설정
    public PerformanceInfoDto(PerformanceDetailDto detail, PerformancePeaceDto peace) {
        this.per_title = detail.getPrfnm() != null ? detail.getPrfnm() : "";
        this.per_startD = detail.getPrfpdfrom() != null ? detail.getPrfpdfrom() : "";
        this.per_endD = detail.getPrfpdto() != null ? detail.getPrfpdto() : "";
        this.per_runT = detail.getPrfruntime() != null ? detail.getPrfruntime() : "";
        this.per_sche = detail.getDtguidance() != null ? detail.getDtguidance() : "";
        this.per_genre = detail.getGenrenm() != null ? detail.getGenrenm() : "";
        this.per_poster = detail.getPoster() != null ? detail.getPoster() : "";
        this.per_peace = detail.getFcltynm() != null ? detail.getFcltynm() : "";
        this.per_region = detail.getArea() != null ? detail.getArea() : "";
        this.per_address = peace.getAdres() != null ? peace.getAdres() : "";
        this.per_latitude = peace.getLa() != null ? peace.getLa() : "";
        this.per_longitude = peace.getLo() != null ? peace.getLo() : "";

        // 티켓 정보 처리
        if (detail.getRelates() != null && !detail.getRelates().isEmpty()) {
            for (RelateDto rel : detail.getRelates()) {
                String name = rel.getRelatenm() != null ? rel.getRelatenm() : "";
                String url = rel.getRelateurl() != null ? rel.getRelateurl() : "";
                this.per_ticket.add(new TicketDto(name, url));
            }
        }
    }
    public PerformanceInfoDto(PerformanceDetailDto detail, PerformancePeaceDto peace, PerformanceStatusDto status) {
        this.per_title = detail.getPrfnm() != null ? detail.getPrfnm() : "";
        this.per_startD = detail.getPrfpdfrom() != null ? detail.getPrfpdfrom() : "";
        this.per_endD = detail.getPrfpdto() != null ? detail.getPrfpdto() : "";
        this.per_runT = detail.getPrfruntime() != null ? detail.getPrfruntime() : "";
        this.per_sche = detail.getDtguidance() != null ? detail.getDtguidance() : "";
        this.per_genre = detail.getGenrenm() != null ? detail.getGenrenm() : "";
        this.per_poster = detail.getPoster() != null ? detail.getPoster() : "";
        this.per_peace = detail.getFcltynm() != null ? detail.getFcltynm() : "";
        this.per_region = detail.getArea() != null ? detail.getArea() : "";
        this.per_address = peace.getAdres() != null ? peace.getAdres() : "";
        this.per_latitude = peace.getLa() != null ? peace.getLa() : "";
        this.per_longitude = peace.getLo() != null ? peace.getLo() : "";
        this.per_rank = status.getRnum() != null ? status.getRnum() : "";

        // 티켓 정보 처리
        if (detail.getRelates() != null && !detail.getRelates().isEmpty()) {
            for (RelateDto rel : detail.getRelates()) {
                String name = rel.getRelatenm() != null ? rel.getRelatenm() : "";
                String url = rel.getRelateurl() != null ? rel.getRelateurl() : "";
                this.per_ticket.add(new TicketDto(name, url));
            }
        }
    }
}
