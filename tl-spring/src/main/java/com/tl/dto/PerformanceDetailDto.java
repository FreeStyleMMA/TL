package com.tl.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Ŭ������ ���ǵ��� ���� �ʵ尡 xml�� �־ ����
public class PerformanceDetailDto { // api���� �޾ƿ� ���� �� ������ ���� Ŭ����

    private String mt20id;        // ����ID
    private String prfnm;         // ������
    private String prfpdfrom;     // ����������
    private String prfpdto;       // ����������
    private String fcltynm;       // �����ü���(�������)
    private String prfcast;       // �����⿬��
    private String prfcrew;       // ����������
    private String prfruntime;    // ���� ��Ÿ��
    private String prfage;        // ���� ���� ����
    private String entrpsnm;      // ��ȹ���ۻ�
    private String entrpsnmP;     // ���ۻ�
    private String entrpsnmA;     // ��ȹ��
    private String entrpsnmH;     // ����
    private String entrpsnmS;     // �ְ�
    private String pcseguidance;  // Ƽ�� ����
    private String poster;        // ������ �̹��� ���
    private String sty;           // �ٰŸ�
    private String area;          // ����
    private String genrenm;       // �帣
    private String openrun;       // ���·� ���� (Y/N)
    private String visit;         // ���� ���� (Y/N)
    private String child;         // �Ƶ� ���� ���� (Y/N)
    private String daehakro;      // ���з� ���� ���� (Y/N)
    private String festival;      // ���� ���� (Y/N)
    private String musicallicense;// ������ ���̼��� ���� (Y/N)
    private String musicalcreate; // ������ â�� ���� (Y/N)
    private String updatedate;    // ���� ������
    private String prfstate;      // ���� ����
    private String mt10id;        // �����ü�ID
    private String dtguidance;    // ���� �ð� �ȳ�

    @JacksonXmlElementWrapper(localName = "styurls") // xml���� ���δ� �±� ���� �迭 ������ ���� �ʿ�
    @JacksonXmlProperty(localName = "styurl")        // XML���� �� �̸����� ����
    private ArrayList<String> styurls;               // �Ұ� �̹��� ���

    @JacksonXmlElementWrapper(localName = "relates") // xml���� ���δ� �±� ���� �迭 ������ ���� �ʿ�
    @JacksonXmlProperty(localName = "relate")       // XML���� �� �̸����� ����
    private ArrayList<RelateDto> relates;           // ����ó ���
}
