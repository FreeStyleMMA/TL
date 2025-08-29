package com.tl.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Ŭ������ ���ǵ��� ���� �ʵ尡 xml�� �־ ����
@JacksonXmlRootElement(localName = "dbs") // xml�����Ϳ��� �� �̸����� �ν�
public class PerformanceDetailWrapperDto { // api���� �޾ƿ� ���� �� ������ �޾ƿ� ������ Ŭ����
    @JacksonXmlProperty(localName = "db") // xml�����Ϳ��� �� �̸����� �ν�
    private PerformanceDetailDto db; // ���� ������ ���
    
}
