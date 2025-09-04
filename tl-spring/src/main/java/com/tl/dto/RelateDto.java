package com.tl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // 클래스에 정의되지 않은 필드가 xml에 있어도 무시
public class RelateDto { // api에서 받아온 공연 상세 정보의 예매처 정보를 담을 클래스
	private String relatenm; // 예매처명
	private String relateurl; // 예매처 URL
}