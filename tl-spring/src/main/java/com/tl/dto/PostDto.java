package com.tl.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDto {

	public Long no;
	public String category;
	public String title;
	public String content;
	public String memberId;
	public Timestamp createdAt;
//	public int totalPages;
	public String media;
}
