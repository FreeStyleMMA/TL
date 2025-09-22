package com.tl.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data

public class PostDto {

	public Long no;
	public String category;
	public String title;
	public String content;
	public String memberId;
	public Timestamp date;
//	public long page;
	public String media;
}
