package com.tl.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketDto { // 공연 티켓 예매처 정보
	private Long perNum ;// 공연 번호(DB)
	private Long ticketNum; // 예매처 번호(DB)
	private String name; //예매처 이름
	private String url; // 예매처 링크

	public TicketDto(String name, String url) {
		this.perNum = (long) 0;
		this.ticketNum = (long) 0;
		this.name = name;
		this.url = url;
	}
	public TicketDto(Long perNum, Long ticketNum, String name, String url) {
		this.perNum = perNum;
		this.ticketNum = ticketNum;
		this.name = name;
		this.url = url;
	}
}
