package com.tl.dto;

import lombok.Data;

@Data
public class TicketDto { // ���� Ƽ�� ����ó ����
	public String name; //����ó �̸�
	public String url; // ����ó ��ũ

	public TicketDto(String name, String url) {
		this.name = name;
		this.url = url;
	}
}
