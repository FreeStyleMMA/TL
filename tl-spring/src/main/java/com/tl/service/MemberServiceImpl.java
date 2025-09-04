package com.tl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tl.dto.LoginRequest;
import com.tl.dto.LoginResponse;
import com.tl.dto.MemberVO;
import com.tl.mapper.MemberMapper;
import com.tl.security.JwtTokenProvider;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MemberServiceImpl {
	@Setter(onMethod_ = @Autowired)
	public MemberMapper mapper;

	@Setter(onMethod_=@Autowired)
	public JwtTokenProvider tokenProvider;
	
	//회원가입 처리

	public MemberVO signUp(MemberVO member) {
		return mapper.signUp(member);
	}
	
	/*
	 * 로그인 처리파트 
	 * id와 pw만 필드로 갖는 loginrequest로 프론트요청 데이터 받고
	 * login 성공 여부, jwt 토큰, 결과 메세지를	LoginResponse로 결과 처리.
	 */
	public LoginResponse signIn(LoginRequest request) {
		
		MemberVO member = mapper.findByMemberId(request.memberId);
	    LoginResponse response = new LoginResponse();

	    if(member==null || !member.getMemberPw().equals(request.getMemberPw())) {
			response.loginSuccess = false;
			response.token = null;
			response.message="로그인 실패";
					return response;
			}
			 String token = tokenProvider.createToken(member.getMemberId());
				response.loginSuccess = true;
				response.token = token;
				response.message="로그인 성공";
		        return response;
	
	}

	public MemberVO findByMemberId(String memberId) {
		return mapper.findByMemberId(memberId);
	}
	
	/* 로그아웃은 프론트에서 jwt 토큰만 지우면 됨.
	 * @PostMapping("/signOut") public void signOut(String memberId) { }
	 */	
}
