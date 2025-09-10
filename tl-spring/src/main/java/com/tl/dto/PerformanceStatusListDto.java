package com.tl.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "boxofs") // xml�����Ϳ��� �� �̸����� �ν�
public class PerformanceStatusListDto { // api�� �޾ƿ� ���� ���� ��Ȳ�� ������ ���
	@JacksonXmlProperty(localName = "boxof") // xml�����Ϳ��� �� �̸����� �ν�
	@JacksonXmlElementWrapper(useWrapping = false) // xml���� ���δ� �±� ���� �迭 ������ ���� �ʿ�
	private List<PerformanceStatusDto> boxof = new ArrayList<PerformanceStatusDto>(); // ���� ������ ���
}
