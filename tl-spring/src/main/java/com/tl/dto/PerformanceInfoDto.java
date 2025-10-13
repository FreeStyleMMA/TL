package com.tl.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	private Long perNum; // ���� ��ȣ(DB)
	private String perGenreC; // ���� �帣 �ڵ�(DB)
	private String perRegionC; // ���� ���� �ڵ�(DB)
	private String perRequestT; // ��û Ÿ��(DB)
	private String perUpdateD; // ���� ���� ��¥(DB)
	private String perId; // ���� ID
	private String perTitle; // ���� ����
	private String perStartD; // ���� ������
	private String perEndD; // ���� ������
	private String perRunT; // ���� ��Ÿ��
	private String perSche; // ���� ����
	private String perGenre; // ���� �帣
	private String perPoster; // ���� ������ �̹���
	private String perPrice; // ���� ����
	private String perPlace; // ���� ���
	private String perAddress; // ���� �ּ�
	private String perLatitude; // ���� ����
	private String perLongitude; // ���� �浵
	private String perRegion; // ���� ����
	private Integer perRank; // ���� ��ŷ
	private List<TicketDto> perTicket = new ArrayList<TicketDto>();// ���� Ƽ�� ����ó ����

	private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[.SSSSSS]");
	private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

	// �ʵ� ������ �� ����
	public PerformanceInfoDto(PerformanceDetailDto detail, PerformancePeaceDto peace, PerformanceRequestDto request) {
		// ��ҵ� �� �Ҵ�
		this.perRequestT = request.getPerRequestT() != null ? request.getPerRequestT() : "";
		this.perUpdateD = parseAndFormatDate(detail.getUpdatedate());
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
		this.perLatitude = peace.getLa() != null ? peace.getLa() : null;
		this.perLongitude = peace.getLo() != null ? peace.getLo() : null;

		// Ƽ�� ���� �Ҵ�
		if (detail.getRelates() != null && !detail.getRelates().isEmpty()) {
			for (RelateDto rel : detail.getRelates()) {
				String name = rel.getRelatenm() != null ? rel.getRelatenm() : "";
				String url = rel.getRelateurl() != null ? rel.getRelateurl() : "";
				this.perTicket.add(new TicketDto(name, url));
			}
		}
	}

	public PerformanceInfoDto(PerformanceDetailDto detail, PerformancePeaceDto peace, PerformanceStatusDto status,
			PerformanceRequestDto request) {
		// ��ҵ� �� �Ҵ�
		this.perRequestT = request.getPerRequestT() != null ? request.getPerRequestT() : "";
		this.perUpdateD = parseAndFormatDate(detail.getUpdatedate());
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
		this.perLatitude = peace.getLa() != null ? peace.getLa() : null;
		this.perLongitude = peace.getLo() != null ? peace.getLo() : null;
		this.perRank = status.getRnum() != null ? status.getRnum() : 0;

		// Ƽ�� ���� �Ҵ�
		if (detail.getRelates() != null && !detail.getRelates().isEmpty()) {
			for (RelateDto rel : detail.getRelates()) {
				String name = rel.getRelatenm() != null ? rel.getRelatenm() : "";
				String url = rel.getRelateurl() != null ? rel.getRelateurl() : "";
				this.perTicket.add(new TicketDto(name, url));
			}
		}
	}

	// ���ڿ� �� LocalDateTime �� ���ϴ� ���� String ��ȯ
	private String parseAndFormatDate(String input) {
		if (input == null || input.isEmpty())
			return "";
		try {
			LocalDateTime dateTime = LocalDateTime.parse(input, INPUT_FORMAT);
			return dateTime.format(OUTPUT_FORMAT);
		} catch (Exception e) {
			// ���� �߻� �� ���� ���ڿ� �״�� ��ȯ
			return input;
		}
	}
}
