package com.tl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PerformanceStatusDto { // ���� ������Ȳ���� �޾ƿ� Ŭ����
	private String prfplcnm;	// ������ �̸�
	private String seatcnt;		// �¼���
	private String rnum;		// ����
	private String poster;		// ������ �̹���
	private String prfpd;		// �����Ⱓ
	private String mt20id;		// ����ID
	private String prfnm;		// ������
	private String cate;		// �帣
	private String prfdtcnt;	// �� ȸ��
	private String area;		// ����
}
