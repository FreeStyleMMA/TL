package com.tl.dto;

import lombok.Data;

@Data
public class PerformanceDto {
	public String mt20id;     // ���� ID (��: PF178134)
	public String prfnm;      // ������ (��: ��¦��¦ �ξ����)
	public String genrenm;    // ���� �帣�� (��: ������)
	public String prfstate;   // ���� ���� (��: ������, �����Ϸ�)
	public String prfpdfrom;  // ���� ������ (��: 20210821)
	public String prfpdto;    // ���� ������ (��: 20240929)
	public String poster;     // ������ �̹��� ���(URL) (��: http://www.kopis.or.kr/upload/pfmPoster/PF_PF178134_210809_125033.PNG)
	public String fcltynm;    // ���� �ü��� / ������� (��: �޹㿣������)
	public String openrun;    // ���·� ���� (Y/N) (��: Y)
	public String area;       // ���� ���� (��: ����Ư����)

}
