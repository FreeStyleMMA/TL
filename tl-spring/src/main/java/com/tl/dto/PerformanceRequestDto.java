package com.tl.dto;

import lombok.Data;

@Data
public class PerformanceRequestDto {
    public String startdate;     // ���� �������� (�ʼ�, 8�ڸ�, ��: 20230101)
    public String enddate;       // ���� �������� (�ʼ�, 8�ڸ�, ��: 20230630)
    public String cpage;         // ���� ������ (�ʼ�, �ִ� 3�ڸ�, ��: 1)
    public String rows;          // �������� ��� �� (�ʼ�, �ִ� 3�ڸ�, �ִ� 100��, ��: 10)
    public String shprfnm;       // ������ (����, �ִ� 100��, URL Encoding �ʿ�, ��: ���)
    public String shprfnmfct;    // �����ü��� (����, �ִ� 100��, URL Encoding �ʿ�, ��: ����������)
    public String shcate;        // �帣�ڵ� (����, �ִ� 4�ڸ�, ��: AAAA)
    public String prfplccd;      // ������ �ڵ� (����, �ִ� 4�ڸ�, ��: FC000001-01)
    public String signgucode;    // ����(�õ�) �ڵ� (����, 2�ڸ�, ��: 11)
    public String signgucodesub; // ����(����) �ڵ� (����, 4�ڸ�, ��: 1111)
    public String kidstate;      // �Ƶ����� ���� (����, 1�ڸ�, Y/N, ���� ���ϸ� ��ü����)
    public String prfstate;      // �������� �ڵ� (����, 2�ڸ�, ��: 01)
    public String openrun;       // ���·� ���� (����, 2�ڸ�, Y/N)
    public String afterdate;     // �ش����� ���� ���/������ �׸� ��� (����, 8�ڸ�, ��: 20230101)
}

