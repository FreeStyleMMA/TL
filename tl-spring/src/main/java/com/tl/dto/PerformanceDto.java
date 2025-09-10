package com.tl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PerformanceDto { // api���� �޾ƿ� ���� ����� ������ �޾ƿ� Ŭ����
	private String mt20id;     // ���� ID (��: PF178134)
	private String prfnm;      // ������ (��: ��¦��¦ �ξ����)
	private String genrenm;    // ���� �帣�� (��: ������)
	private String prfstate;   // ���� ���� (��: ������, �����Ϸ�)
	private String prfpdfrom;  // ���� ������ (��: 20210821)
	private String prfpdto;    // ���� ������ (��: 20240929)
	private String poster;     // ������ �̹��� ���(URL) (��: http://www.kopis.or.kr/upload/pfmPoster/PF_PF178134_210809_125033.PNG)
	private String fcltynm;    // ���� �ü��� / ������� (��: �޹㿣������)
	private String openrun;    // ���·� ���� (Y/N) (��: Y)
	private String area;       // ���� ���� (��: ����Ư����)

}
