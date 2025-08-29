package com.tl.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // 클래스에 정의되지 않은 필드가 xml에 있어도 무시
@JacksonXmlRootElement(localName = "dbs") // xml데이터에서 이 이름으로 인식
public class PerformanceDetailWrapperDto { // api에서 받아온 공연 상세 정보를 받아올 껍데기 클래스
    @JacksonXmlProperty(localName = "db") // xml데이터에서 이 이름으로 인식
    private PerformanceDetailDto db; // 공연 데이터 목록
    
}
