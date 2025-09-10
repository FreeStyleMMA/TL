package com.tl.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Ŭ������ ���ǵ��� ���� �ʵ尡 xml�� �־ ����
public class PerformancePeaceDto { // api���� �޾ƿ� �����ü� �� ������ ���� Ŭ����
    private String fcltynm;       // �����ü���
    private String mt10id;        // �����ü�ID
    private String mt13cnt;       // ������ ��
    private String fcltychartr;   // �ü�Ư��
    private String opende;        // ��������
    private String seatscale;     // ���� ��
    private String telno;         // ��ȭ��ȣ
    private String relateurl;     // Ȩ������
    private String adres;         // �ּ�
    private String la;            // ����
    private String lo;            // �浵
    private String restaurant;    // ������� ����
    private String cafe;          // ī�� ����
    private String store;         // ������ ����
    private String nolibang;      // ���̹� ����
    private String suyu;          // ������ ����
    private String parkbarrier;   // ��ֽü�-������
    private String restbarrier;   // ��ֽü�-ȭ���
    private String runwbarrier;   // ��ֽü�-����
    private String elevbarrier;   // ��ֽü�-����������
    private String parkinglot;    // �����ü�

    @JacksonXmlElementWrapper(localName = "mt13s")
    @JacksonXmlProperty(localName = "mt13")
    private List<StageDto> mt13s;   // ������ ���
}
