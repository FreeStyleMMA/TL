package com.tl.dto;

import java.util.Date;

import lombok.Data;

@Data

public class PostDto {

	public long no;
	public String category;
	public String title;
	public String content;
	public String memberId;
	public Date date;
//	public long page;
	public String media;
}
