package com.tl.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/api/*")
@CrossOrigin(value = "http://localhost:3000", allowCredentials = "true")
public class MemberController {

	@Setter(onMethod_ = @Autowired)
	public MemberService service;

	@Setter(onMethod_=@Autowired)
	public JwtTokenProvider jwtUtil;


	@PostMapping("/signUp")
	public MemberVO signUp(@RequestBody MemberVO member) {
		return service.signUp(member);
	}

	//로그인 처리
	@PostMapping("/signIn")
	// http 리턴을 위한 ResponseEntity 사용
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
		log.info("로그인 요청 id:" + request.memberId);
		LoginResponse loginResponse = service.signIn(request);
		log.info("http요청: "+request);
		// 로그인 유효성 확인
		log.info("로그인 성공 여부:"+loginResponse.loginSuccess);
		if (loginResponse.loginSuccess == true) {
			// 쿠키에 토큰 저장 jwt:token 형태.
			 jwtUtil.addJwtToCookie(loginResponse.token, response);
		}
		loginResponse.setToken(null);
		// http 형태로 리턴. header에는 jwt가 있는 쿠키, body에 loginResponse.
		return ResponseEntity.ok(loginResponse);
		
	}

}
