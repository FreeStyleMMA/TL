package com.tl.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class MyPostResponse {
	public Long no;
	public String title;
	public String content;
	public Timestamp createdAt;
}
