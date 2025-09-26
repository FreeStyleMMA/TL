package com.tl.dto;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PerformanceRequestDto { // api���� ���� ����� �޾ƿ� �� ��û�� �Ű������� ���� Ŭ����
	private String perRequestD = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE); // ��û ��¥(���� ��¥)
    private String startdate = "";     // ���� �������� (�ʼ�, 8�ڸ�, ��: 20230101)
    private String enddate = "";       // ���� �������� (�ʼ�, 8�ڸ�, ��: 20230630)
    private String cpage = "";         // ���� ������ (�ʼ�, �ִ� 3�ڸ�, ��: 1)
    private String rows = "";          // �������� ��� �� (�ʼ�, �ִ� 3�ڸ�, �ִ� 100��, ��: 10)
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
    private String perRequestT = "";   // ��õ ���� ����
}


