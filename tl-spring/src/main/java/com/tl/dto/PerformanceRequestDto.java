package com.tl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PerformanceRequestDto { // api���� ���� ����� �޾ƿ� �� ��û�� �Ű������� ���� Ŭ����
    private String startdate = "";     // ���� �������� (�ʼ�, 8�ڸ�, ��: 20230101)
    private String enddate = "";       // ���� �������� (�ʼ�, 8�ڸ�, ��: 20230630)
    private Integer cpage = 0;         // ���� ������ (�ʼ�, �ִ� 3�ڸ�, ��: 1)
    private Integer rows = 0;          // �������� ��� �� (�ʼ�, �ִ� 3�ڸ�, �ִ� 100��, ��: 10)
    private String shprfnm = "";       // ������ (����, �ִ� 100��, URL Encoding �ʿ�, ��: ���)
    private String shprfnmfct = "";    // �����ü��� (����, �ִ� 100��, URL Encoding �ʿ�, ��: ����������)
    private String shcate = "";        // �帣�ڵ� (����, �ִ� 4�ڸ�, ��: AAAA)
    private String prfplccd = "";      // ������ �ڵ� (����, �ִ� 4�ڸ�, ��: FC000001-01)
    private String signgucode = "";    // ����(�õ�) �ڵ� (����, 2�ڸ�, ��: 11)
    private String signgucodesub = ""; // ����(����) �ڵ� (����, 4�ڸ�, ��: 1111)
    private String kidstate = "";      // �Ƶ����� ���� (����, 1�ڸ�, Y/N, ���� ���ϸ� ��ü����)
    private String prfstate = "";      // �������� �ڵ� (����, 2�ڸ�, ��: 01)
    private String openrun = "";       // ���·� ���� (����, 2�ڸ�, Y/N)
    private String afterdate = "";     // �ش����� ���� ���/������ �׸� ��� (����, 8�ڸ�, ��: 20230101)
    private String perRequestT = "";   // ��õ ���� ����(DB)
    private Integer startIndex = 0; // ���� �ε���(DB)
    
    public PerformanceRequestDto(String startdate, String enddate, String shcate, String perRequestT){
    	this.startdate = startdate;
    	this.enddate = enddate;
    	this.shcate = shcate;
    	this.perRequestT = perRequestT;
    }
    public PerformanceRequestDto(String startdate, String enddate, Integer cpage, Integer rows, String shcate){
    	this.startdate = startdate;
    	this.enddate = enddate;
    	this.cpage = cpage;
    	this.rows = rows;
    	this.shcate = shcate;
    }
    public PerformanceRequestDto() {
    }

	public void setCpage(Integer cpage) {
        this.cpage = cpage;
        this.startIndex = (this.cpage - 1) * this.rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
        this.startIndex = (this.cpage - 1) * this.rows;
    }
}


