package com.tl.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyDto {

	public long no;
	public long originNo;
	public String memberId;
	public String content;
	public Date date;
}
