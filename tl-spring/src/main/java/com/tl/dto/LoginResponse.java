package com.tl.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
	//로그인 성공 여부
	public boolean loginSuccess;
	public String token;
	//에러 메세지
	public String message;
	public String role;
}
