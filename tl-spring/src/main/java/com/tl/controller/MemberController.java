package com.tl.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Setter(onMethod_=@Autowired)
	public JwtTokenProvider tokenProvider;

	@PostMapping("/signUp")
	public MemberVO signUp(@RequestBody MemberVO member) {
		return service.signUp(member);
	}

	
	@PostMapping("/signIn")
	public LoginResponse signIn(@RequestBody LoginRequest request) {
		return service.signIn(request);
	}
	
}
