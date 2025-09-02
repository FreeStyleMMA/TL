package com.tl.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class PerformanceInfoDto {
	private String per_title; // ���� ����
	private String per_startD; // ���� ������
	private String per_endD; // ���� ������
	private String per_peace; // ���� ���
	private String per_runT; // ���� ��Ÿ��
	private String per_sche; // ���� ����
	private String per_genre; // ���� �帣
	private String per_poster; // ���� ������ �̹���
	private Map<String, String> per_ticket = new HashMap<>();// ���� ���� ��ũ

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
