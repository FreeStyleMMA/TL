package com.tl.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReplyDto {

	public Long no;
	public Long originNo;
	public String memberId;
	public String content;
	public Timestamp date;
}
