package com.tl.dto;

import lombok.Data;

@Data
public class SignUpRequest {
	public String memberId;
	public String memberPw;
	public String memberName;
}
