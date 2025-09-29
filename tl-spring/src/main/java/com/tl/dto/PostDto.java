package com.tl.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor      
@AllArgsConstructor
@Builder
public class PostDto {

	public Long no;
	public String category;
	public String title;
	public String content;
	public String memberId;
	public Timestamp createdAt;
	public String media;
}
