package com.tl.dto;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "dbs") // xml�����Ϳ��� �� �̸����� �ν�
public class PerformanceListDto { // api���� �޾ƿ� ���� ����� �޾ƿ� Ŭ����
    
    @JacksonXmlProperty(localName = "db") // xml�����Ϳ��� �� �̸����� �ν�
    @JacksonXmlElementWrapper(useWrapping = false) // xml���� ���δ� �±� ���� �迭 ������ ���� �ʿ�
    private List<PerformanceDto> db; // ���� ������ ���
}
