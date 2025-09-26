package com.tl.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketDto { // ���� Ƽ�� ����ó ����
	private Long perNum ;// ���� ��ȣ(DB)
	private Long ticketNum; // ����ó ��ȣ(DB)
	private String name; //����ó �̸�
	private String url; // ����ó ��ũ

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
