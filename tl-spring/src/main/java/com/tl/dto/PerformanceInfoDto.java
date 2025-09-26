package com.tl.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.tl.util.CodeMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceInfoDto {
	private String perRequestD;		// 공연 추가 날짜(DB)
	private String perGenreC;	// 공연 장르 코드(DB)
	private String perRegionC;	// 공연 지역 코드(DB)
	private String perRequestT;	// 요청 타입(DB)
	private Long perNum;			// 공연 번호(DB)
	private String perId;			// 공연 ID
	private String perTitle; 		// 공연 제목
	private String perStartD; 		// 공연 시작일
	private String perEndD; 		// 공연 종료일
	private String perRunT; 		// 공연 런타임
	private String perSche; 		// 공연 일정
	private String perGenre; 		// 공연 장르
	private String perPoster; 		// 공연 포스터 이미지
	private String perPrice;		// 공연 가격
	private String perPlace; 		// 공연 장소
	private String perAddress; 		// 공연 주소
	private String perLatitude;		// 공연 위도
	private String perLongitude;    // 공연 경도
	private String perRegion;		// 공연 지역
	private Integer perRank;		// 공연 랭킹
	private List<TicketDto> perTicket = new ArrayList<TicketDto>();// 공연 티켓 예매처 정보

	//필드 데이터 값 설정
    public PerformanceInfoDto(PerformanceDetailDto detail, PerformancePeaceDto peace, PerformanceRequestDto request) {
    	this.perRequestD = request.getPerRequestD() != null ? request.getPerRequestD() : "";
    	this.perRequestT = request.getPerRequestT() != null ? request.getPerRequestT() : "";
    	this.perTitle = detail.getPrfnm() != null ? detail.getPrfnm() : "";
    	this.perId = detail.getMt20id() != null ? detail.getMt20id() : "";    	
        this.perStartD = detail.getPrfpdfrom() != null ? detail.getPrfpdfrom() : "";
        this.perEndD = detail.getPrfpdto() != null ? detail.getPrfpdto() : "";
        this.perRunT = detail.getPrfruntime() != null ? detail.getPrfruntime() : "";
        this.perSche = detail.getDtguidance() != null ? detail.getDtguidance() : "";
        this.perGenre = detail.getGenrenm() != null ? detail.getGenrenm() : "";
        this.perGenreC = CodeMapper.getGenreCode(perGenre);
        this.perPoster = detail.getPoster() != null ? detail.getPoster() : "";
        this.perPrice = detail.getPcseguidance() != null ? detail.getPcseguidance() : "전석무료";
        this.perPlace = detail.getFcltynm() != null ? detail.getFcltynm() : "";
        this.perRegion = detail.getArea() != null ? detail.getArea() : "";
        this.perRegionC = CodeMapper.getRegionCode(perRegion);
        this.perAddress = peace.getAdres() != null ? peace.getAdres() : "";
        this.perLatitude = peace.getLa() != null ? peace.getLa() : "";
        this.perLongitude = peace.getLo() != null ? peace.getLo() : "";
        
        // 티켓 정보 처리
        if (detail.getRelates() != null && !detail.getRelates().isEmpty()) {
            for (RelateDto rel : detail.getRelates()) {
                String name = rel.getRelatenm() != null ? rel.getRelatenm() : "";
                String url = rel.getRelateurl() != null ? rel.getRelateurl() : "";
                this.perTicket.add(new TicketDto(name, url));
            }
        }
    }
    public PerformanceInfoDto(PerformanceDetailDto detail, PerformancePeaceDto peace, PerformanceStatusDto status, PerformanceRequestDto request) {
    	this.perRequestD = request.getPerRequestD() != null ? request.getPerRequestD() : "";
    	this.perRequestT = request.getPerRequestT() != null ? request.getPerRequestT() : "";
    	this.perTitle = detail.getPrfnm() != null ? detail.getPrfnm() : "";
    	this.perId = detail.getMt20id() != null ? detail.getMt20id() : "";
    	this.perStartD = detail.getPrfpdfrom() != null ? detail.getPrfpdfrom() : "";
        this.perEndD = detail.getPrfpdto() != null ? detail.getPrfpdto() : "";
        this.perRunT = detail.getPrfruntime() != null ? detail.getPrfruntime() : "";
        this.perSche = detail.getDtguidance() != null ? detail.getDtguidance() : "";
        this.perGenre = detail.getGenrenm() != null ? detail.getGenrenm() : "";
        this.perGenreC = CodeMapper.getGenreCode(perGenre);
        this.perPoster = detail.getPoster() != null ? detail.getPoster() : "";
        this.perPrice = detail.getPcseguidance() != null ? detail.getPcseguidance() : "전석무료";
        this.perPlace = detail.getFcltynm() != null ? detail.getFcltynm() : "";
        this.perRegion = detail.getArea() != null ? detail.getArea() : "";
        this.perRegionC = CodeMapper.getRegionCode(perRegion);
        this.perAddress = peace.getAdres() != null ? peace.getAdres() : "";
        this.perLatitude = peace.getLa() != null ? peace.getLa() : "";
        this.perLongitude = peace.getLo() != null ? peace.getLo() : "";
        this.perRank = status.getRnum() != null ? status.getRnum() : 0;

        // 티켓 정보 처리
        if (detail.getRelates() != null && !detail.getRelates().isEmpty()) {
            for (RelateDto rel : detail.getRelates()) {
                String name = rel.getRelatenm() != null ? rel.getRelatenm() : "";
                String url = rel.getRelateurl() != null ? rel.getRelateurl() : "";
                this.perTicket.add(new TicketDto(name, url));
            }
        }
    }
}
