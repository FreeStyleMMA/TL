package com.tl.service;

import com.tl.dto.LoginResponse;
import com.tl.dto.MemberVO;
import com.tl.dto.MyInfoResponse;
import com.tl.dto.SignUpRequest;
import com.tl.dto.SignUpResponse;

public interface MemberService {
	public SignUpResponse signUp(SignUpRequest request);
    public LoginResponse signIn(String memberId, String memberPw);
	public MemberVO findByMemberId(String memberId);
	public MyInfoResponse getMyInfo(String memberId);
}
