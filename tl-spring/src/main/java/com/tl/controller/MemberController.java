package com.tl.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tl.dto.LoginRequest;
import com.tl.dto.LoginResponse;
import com.tl.dto.MemberVO;
import com.tl.security.JwtTokenProvider;
import com.tl.service.MemberService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RestController
@Log4j
@RequestMapping("/member")
public class MemberController {

	@Setter(onMethod_ = @Autowired)
	public MemberService service;

	@Autowired
	public JwtTokenProvider tokenProvider;

	@PostMapping("/signUp")
	public MemberVO signUp(@RequestBody MemberVO member) {
		return service.signUp(member);
	}

	@PostMapping("/signIn")
//	Http 헤더에 토큰을 넣어서 전달하기 위한 ResponseEntity사용
	public ResponseEntity<LoginResponse> signIn(@RequestBody LoginRequest request, HttpServletResponse response) {
		LoginResponse loginResponse = service.signIn(request);
		//로그인 유효성 확인
		if(loginResponse.loginSuccess) {
			//token을 response(Http header)에 추가
			tokenProvider.addJwtToCookie(loginResponse.token, response);
		}
//		bodyResponse에는 token제거
		LoginResponse bodyResponse = new LoginResponse();
		bodyResponse.setLoginSuccess(loginResponse.loginSuccess);
		bodyResponse.setToken(null);
		bodyResponse.setMessage(loginResponse.message);
		
		return ResponseEntity.ok(bodyResponse);
		
	}

}
