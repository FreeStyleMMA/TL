package com.tl.dto;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "dbs") // xml데이터에서 이 이름으로 인식
public class PerformanceListDto { // api에서 받아온 공연 목록을 받아올 클래스
    
    @JacksonXmlProperty(localName = "db") // xml데이터에서 이 이름으로 인식
    @JacksonXmlElementWrapper(useWrapping = false) // xml에서 감싸는 태그 없이 배열 데이터 사용시 필요
    private List<PerformanceDto> db; // 공연 데이터 목록
}
