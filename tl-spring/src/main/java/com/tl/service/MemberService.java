package com.tl.service;

import com.tl.dto.FavoriteDTO;
import com.tl.dto.LoginDTO;
import com.tl.dto.LoginRequest;
import com.tl.dto.LoginResponse;
import com.tl.dto.SignUpRequest;
import com.tl.dto.SignUpResponse;

public interface MemberService {
	public SignUpResponse signUp(SignUpRequest request);
	public LoginResponse signIn(LoginRequest request);
	public LoginDTO findByMemberId(String memberId);
}
