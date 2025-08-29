package com.tl.mapper;

import com.tl.dto.LoginRequest;
import com.tl.dto.LoginResponse;
import com.tl.dto.MemberVO;

public interface MemberMapper {
	public MemberVO signUp(MemberVO member);
	public LoginResponse signIn(LoginRequest request);
	//jwt 인증용
	public MemberVO findByMemberId(String memberId);
}
