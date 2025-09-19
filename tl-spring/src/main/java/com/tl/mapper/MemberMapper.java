package com.tl.mapper;

import com.tl.dto.LoginDTO;
import com.tl.dto.LoginRequest;
import com.tl.dto.LoginResponse;
import com.tl.dto.SignUpRequest;

public interface MemberMapper {
	public void signUp(SignUpRequest request);
	public LoginResponse signIn(LoginRequest request);
	//로그인 및 jwt 토큰 인증에 사용
	public LoginDTO findByMemberId(String memberId);
}
