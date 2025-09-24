package com.tl.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class MyInfoResponse {
	public String memberName;
	public String memberId;
	public Timestamp createdAt;	
}
