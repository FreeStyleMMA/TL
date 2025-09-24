package com.tl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tl.dto.LoginDTO;
import com.tl.dto.LoginRequest;
import com.tl.dto.LoginResponse;
import com.tl.dto.MyInfoResponse;
import com.tl.dto.SignUpRequest;
import com.tl.dto.SignUpResponse;
import com.tl.mapper.MemberMapper;
import com.tl.security.JwtTokenProvider;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MemberServiceImpl implements MemberService {
	@Setter(onMethod_ = @Autowired)
	public MemberMapper mapper;

	@Setter(onMethod_=@Autowired)
	public JwtTokenProvider jwtProvider;
	
	//회원가입 처리
	public SignUpResponse signUp(SignUpRequest request) {
	    LoginDTO existing = mapper.findByMemberId(request.getMemberId());
		if(existing != null) {
			return SignUpResponse.builder()
					.SignUpSuccess(false)
					.message("아이디 중복")
					.build();
		}else {
			 mapper.signUp(request);
			 return SignUpResponse.builder()
						.SignUpSuccess(true)
						.message("회원가입 성공!")
						.build();
		}
	}
	
	@Setter(onMethod_ = @Autowired)
	PasswordEncoder passwordEncoder;
	
	/*
	 * 로그인 처리파트 
	 * id와 pw만 필드로 갖는 loginrequest로 프론트요청 데이터 받고
	 * login 성공 여부, jwt 토큰, 결과 메세지를	LoginResponse로 결과 처리.
	 */
	public LoginResponse signIn(LoginRequest request) {
//		http에서 요청을 받고 요청에서 memberId를 추출한다. 
//		추출한 memberId로 mapper에서 로그인에 필요한 정보(memberId,memberPw,role)를 가져와 
//		각각 username,password,authorities로 바인딩한 LoginDTO객체 user를 만든다. >>filter 등 내장 함수와 변수를 통일하기 위함.  
		LoginDTO user = mapper.findByMemberId(request.memberId);
		log.info("DB 조회 정보: " + user);
//		if (user != null && user.getUsername() != null && passwordEncoder.matches(request.getMemberPw(),user.getUsername())) {
			if (user != null && user.getUsername() != null && request.getMemberPw().equals(user.getPassword())) {
			String token = jwtProvider.createToken(user.getUsername());
			 return LoginResponse.builder() // 로그인 결과와 role 등을 만들기.
		                .loginSuccess(true)
		                .token(token)
		                .role(user.getAuthorities())
		                .message("로그인 성공")
		                .build();
		} else {
			return LoginResponse.builder()
					.loginSuccess(false)
					.message("로그인 실패")
					.token(null)
					.role(null)
					.build();
		}
	}

	public LoginDTO findByMemberId(String memberId) {
		return mapper.findByMemberId(memberId);
	}
	
	public MyInfoResponse getMyInfo(String memberId){ // mypage에서 myInfo 정보 조회
		return mapper.getMyInfo(memberId);
	}
	

}
