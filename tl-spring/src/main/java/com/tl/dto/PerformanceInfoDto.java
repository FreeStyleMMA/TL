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
	private String perRequestD;		// ���� �߰� ��¥(DB)
	private String perGenreC;	// ���� �帣 �ڵ�(DB)
	private String perRegionC;	// ���� ���� �ڵ�(DB)
	private String perRequestT;	// ��û Ÿ��(DB)
	private Long perNum;			// ���� ��ȣ(DB)
	private String perId;			// ���� ID
	private String perTitle; 		// ���� ����
	private String perStartD; 		// ���� ������
	private String perEndD; 		// ���� ������
	private String perRunT; 		// ���� ��Ÿ��
	private String perSche; 		// ���� ����
	private String perGenre; 		// ���� �帣
	private String perPoster; 		// ���� ������ �̹���
	private String perPrice;		// ���� ����
	private String perPlace; 		// ���� ���
	private String perAddress; 		// ���� �ּ�
	private String perLatitude;		// ���� ����
	private String perLongitude;    // ���� �浵
	private String perRegion;		// ���� ����
	private Integer perRank;		// ���� ��ŷ
	private List<TicketDto> perTicket = new ArrayList<TicketDto>();// ���� Ƽ�� ����ó ����

	//�ʵ� ������ �� ����
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
        this.perPrice = detail.getPcseguidance() != null ? detail.getPcseguidance() : "��������";
        this.perPlace = detail.getFcltynm() != null ? detail.getFcltynm() : "";
        this.perRegion = detail.getArea() != null ? detail.getArea() : "";
        this.perRegionC = CodeMapper.getRegionCode(perRegion);
        this.perAddress = peace.getAdres() != null ? peace.getAdres() : "";
        this.perLatitude = peace.getLa() != null ? peace.getLa() : "";
        this.perLongitude = peace.getLo() != null ? peace.getLo() : "";
        
        // Ƽ�� ���� ó��
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
        this.perPrice = detail.getPcseguidance() != null ? detail.getPcseguidance() : "��������";
        this.perPlace = detail.getFcltynm() != null ? detail.getFcltynm() : "";
        this.perRegion = detail.getArea() != null ? detail.getArea() : "";
        this.perRegionC = CodeMapper.getRegionCode(perRegion);
        this.perAddress = peace.getAdres() != null ? peace.getAdres() : "";
        this.perLatitude = peace.getLa() != null ? peace.getLa() : "";
        this.perLongitude = peace.getLo() != null ? peace.getLo() : "";
        this.perRank = status.getRnum() != null ? status.getRnum() : 0;

        // Ƽ�� ���� ó��
        if (detail.getRelates() != null && !detail.getRelates().isEmpty()) {
            for (RelateDto rel : detail.getRelates()) {
                String name = rel.getRelatenm() != null ? rel.getRelatenm() : "";
                String url = rel.getRelateurl() != null ? rel.getRelateurl() : "";
                this.perTicket.add(new TicketDto(name, url));
            }
        }
    }
}
