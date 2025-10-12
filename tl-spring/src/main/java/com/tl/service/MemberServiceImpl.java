package com.tl.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tl.dto.LoginResponse;
import com.tl.dto.MemberVO;
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
	@Setter(onMethod_=@Autowired)
	public MemberMapper mapper;

	@Setter(onMethod_=@Autowired)
	public JwtTokenProvider jwtProvider;
	
	@Setter(onMethod_ = @Autowired)
	BCryptPasswordEncoder passwordEncoder;
	
	//회원가입 처리
	public SignUpResponse signUp(SignUpRequest request) {
		MemberVO existing = mapper.findByMemberId(request.getMemberId());
		if(existing != null) { // 중복 아이디 체크
			return SignUpResponse.builder()
					.SignUpSuccess(false)
					.message("아이디 중복")
					.build();
		}else {//회원가입 성공 처리
			request.setMemberPw(passwordEncoder.encode(request.getMemberPw())); // 비밀번호 암호화
			 mapper.signUp(request);
			 return SignUpResponse.builder()
						.SignUpSuccess(true)
						.message("회원가입 성공!")
						.build();
		}
	}
	

	
	/*
	 * 로그인 처리파트 
	 * id와 pw만 필드로 갖는 loginrequest로 프론트요청 데이터 받고
	 * login 성공 여부, jwt 토큰, 결과 메세지를	LoginResponse로 결과 처리.
	 */
//	public LoginResponse signIn(LoginRequest request) {
////		http에서 요청을 받고 요청에서 memberId를 추출한다. 
////		추출한 memberId로 mapper에서 로그인에 필요한 정보(memberId,memberPw,role)를 가져와 
////		각각 username,password,authorities로 바인딩한 LoginDTO객체 user를 만든다. >>filter 등 내장 함수와 변수를 통일하기 위함.  
//		LoginDTO user = mapper.findByMemberId(request.memberId);
//		log.info("DB 조회 정보: " + user);
////		if (user != null && user.getUsername() != null && passwordEncoder.matches(request.getMemberPw(),user.getUsername())) {
//			if (user != null && user.getUsername() != null && request.getMemberPw().equals(user.getPassword())) {
//			String token = jwtProvider.createToken(user.getUsername());
//			 return LoginResponse.builder() // 로그인 결과와 role 등을 만들기.
//		                .loginSuccess(true)
//		                .token(token)
//		                .role(user.getAuthorities())
//		                .message("로그인 성공")
//		                .build();
//		} else {
//			return LoginResponse.builder()
//					.loginSuccess(false)
//					.message("로그인 실패")
//					.token(null)
//					.role(null)
//					.build();
//		}
//	}
    public LoginResponse signIn(String memberId, String memberPw) {
    	MemberVO member = mapper.findByMemberId(memberId);
   System.out.println("DB에서 조회한 member:"+member);
   
        if (member == null) {
            System.out.println("로그인 실패");
            return null;
        }
        if (!passwordEncoder.matches(memberPw, member.getMemberPw())) {
            System.out.println("비밀번호 불일치");
            return  LoginResponse.builder()
            		.loginSuccess(true)
            		.accessToken(null)
            		.refreshToken(null)
            		.message("로그인 성공")
            		.build();
        }
        String accessToken = jwtProvider.createAccessToken( // 로그인 하면서 톻큰 생성
                member.getMemberId(),
                member.getRole()
        );
        //refreshToken 존재여부 확인 
        String exostingRefresh = mapper.checkRefreshToken(memberId);
        if(exostingRefresh != null) {
        	return  LoginResponse.builder()
            		.loginSuccess(true)
            		.accessToken(accessToken)
            		.refreshToken(exostingRefresh)
            		.message("로그인 성공")
            		.build();
        }
        
        String refreshToken = jwtProvider.createRefreshToken(member.getMemberId()); // refreshToken 생성
        //refrestToken DB에 저장
        Date expiration = jwtProvider.extractExpiration(refreshToken);
        mapper.saveOrUpdate(member.getMemberId(), refreshToken, expiration);

        return  LoginResponse.builder()
        		.loginSuccess(true)
        		.accessToken(accessToken)
        		.refreshToken(refreshToken)
        		.message("로그인 성공")
        		.build();
        
    }


	public MemberVO findByMemberId(String memberId) {
		return mapper.findByMemberId(memberId);
	}
	
	public MyInfoResponse getMyInfo(String memberId){ // mypage에서 myInfo 정보 조회
		return mapper.getMyInfo(memberId);
	}
	

}
