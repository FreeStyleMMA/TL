package com.tl.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PerformanceInfoDto {
	private String per_title; 		// ���� ����
	private String per_startD; 		// ���� ������
	private String per_endD; 		// ���� ������
	private String per_runT; 		// ���� ��Ÿ��
	private String per_sche; 		// ���� ����
	private String per_genre; 		// ���� �帣
	private String per_poster; 		// ���� ������ �̹���
	private String per_peace; 		// ���� ���
	private String per_address; 	// ���� �ּ�
	private String per_latitude;	// ���� ����
	private String per_longitude;   // ���� �浵
	private String per_region;		// ���� ����
	private String per_rank;		// ���� ��ŷ
	private List<TicketDto> per_ticket = new ArrayList<TicketDto>();// ���� Ƽ�� ����ó ����

	//�ʵ� ������ �� ����
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

        // Ƽ�� ���� ó��
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

        // Ƽ�� ���� ó��
        if (detail.getRelates() != null && !detail.getRelates().isEmpty()) {
            for (RelateDto rel : detail.getRelates()) {
                String name = rel.getRelatenm() != null ? rel.getRelatenm() : "";
                String url = rel.getRelateurl() != null ? rel.getRelateurl() : "";
                this.per_ticket.add(new TicketDto(name, url));
            }
        }
    }
}
