package com.tl.service;

import com.tl.dto.LoginDTO;
import com.tl.dto.LoginRequest;
import com.tl.dto.LoginResponse;
import com.tl.dto.MemberVO;

public interface MemberService {
	public MemberVO signUp(MemberVO member);
	public LoginResponse signIn(LoginRequest request);
	public LoginDTO findByMemberId(String memberId);

}
