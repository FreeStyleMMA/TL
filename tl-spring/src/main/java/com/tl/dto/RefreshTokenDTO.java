package com.tl.dto;

import java.util.Date;

import lombok.Data;

@Data
public class RefreshTokenDTO {
	private String memberId;
	private String token;
	private Date expiration;
}
