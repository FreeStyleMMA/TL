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
@JacksonXmlRootElement(localName = "boxofs") // xml데이터에서 이 이름으로 인식
public class PerformanceStatusListDto { // api로 받아온 공연 예매 현황판 데이터 목록
	@JacksonXmlProperty(localName = "boxof") // xml데이터에서 이 이름으로 인식
	@JacksonXmlElementWrapper(useWrapping = false) // xml에서 감싸는 태그 없이 배열 데이터 사용시 필요
	private List<PerformanceStatusDto> boxof = new ArrayList<PerformanceStatusDto>(); // 공연 데이터 목록
}
