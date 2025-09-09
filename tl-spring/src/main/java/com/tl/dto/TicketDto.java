package com.tl.dto;

import lombok.Data;

@Data
public class TicketDto { // 공연 티켓 예매처 정보
	public String name; //예매처 이름
	public String url; // 예매처 링크

	public TicketDto(String name, String url) {
		this.name = name;
		this.url = url;
	}
}
