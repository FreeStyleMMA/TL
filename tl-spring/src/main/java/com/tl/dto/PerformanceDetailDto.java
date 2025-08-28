package com.tl.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Ŭ������ ���ǵ��� ���� �ʵ尡 xml�� �־ ����
public class PerformanceDetailDto {

    public String mt20id;        // ����ID
    public String prfnm;         // ������
    public String prfpdfrom;     // ����������
    public String prfpdto;       // ����������
    public String fcltynm;       // �����ü���(�������)
    public String prfcast;       // �����⿬��
    public String prfcrew;       // ����������
    public String prfruntime;    // ���� ��Ÿ��
    public String prfage;        // ���� ���� ����
    public String entrpsnm;      // ��ȹ���ۻ�
    public String entrpsnmP;     // ���ۻ�
    public String entrpsnmA;     // ��ȹ��
    public String entrpsnmH;     // ����
    public String entrpsnmS;     // �ְ�
    public String pcseguidance;  // Ƽ�� ����
    public String poster;        // ������ �̹��� ���
    public String sty;           // �ٰŸ�
    public String area;          // ����
    public String genrenm;       // �帣
    public String openrun;       // ���·� ���� (Y/N)
    public String visit;         // ���� ���� (Y/N)
    public String child;         // �Ƶ� ���� ���� (Y/N)
    public String daehakro;      // ���з� ���� ���� (Y/N)
    public String festival;      // ���� ���� (Y/N)
    public String musicallicense;// ������ ���̼��� ���� (Y/N)
    public String musicalcreate; // ������ â�� ���� (Y/N)
    public String updatedate;    // ���� ������
    public String prfstate;      // ���� ����
    public String mt10id;        // �����ü�ID
    public String dtguidance;    // ���� �ð� �ȳ�

    @JacksonXmlElementWrapper(localName = "styurls") // xml���� ���δ� �±� ���� �迭 ������ ���� �ʿ�
    @JacksonXmlProperty(localName = "styurl")        // XML���� �� �̸����� ����
    private ArrayList<String> styurls;               // �Ұ� �̹��� ���

    @JacksonXmlElementWrapper(localName = "relates") // xml���� ���δ� �±� ���� �迭 ������ ���� �ʿ�
    @JacksonXmlProperty(localName = "relate")       // XML���� �� �̸����� ����
    private ArrayList<RelateDto> relates;           // ����ó ���
}
