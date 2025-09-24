package com.tl.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tl.dto.LoginRequest;
import com.tl.dto.LoginResponse;
import com.tl.dto.MyInfoResponse;
import com.tl.dto.SignUpRequest;
import com.tl.dto.SignUpResponse;
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


	@PostMapping(value="/signUp",produces = "application/json; charset=UTF-8")
	public SignUpResponse signUp(@RequestBody SignUpRequest request) {
		return service.signUp(request);
	}

	//로그인 처리
	@PostMapping("/signIn")
	// http 리턴을 위한 ResponseEntity 사용
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
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
	
	@GetMapping("getMyInfo")
	public  MyInfoResponse getMyInfo(@RequestParam String memberId){
		return service.getMyInfo(memberId);
	}

}
