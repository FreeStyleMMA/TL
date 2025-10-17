package com.tl.controller;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tl.dto.LoginResponse;
import com.tl.dto.MemberVO;
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
	public JwtTokenProvider jwtProvider;


	// 회원가입
	@PostMapping(value="/signUp",produces = "application/json; charset=UTF-8")
	public SignUpResponse signUp(@RequestBody SignUpRequest request) {
		return service.signUp(request);
	}

	//로그인 처리
//	@PostMapping("/signIn")
//	// http 리턴을 위한 ResponseEntity 사용
//	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) {
//		LoginResponse loginResponse = service.signIn(request);
//		log.info("http요청: "+request);
//		// 로그인 유효성 확인
//		log.info("로그인 성공 여부:"+loginResponse.loginSuccess);
//		if (loginResponse.loginSuccess == true) {
//			// 쿠키에 토큰 저장 jwt:token 형태.
//			 jwtUtil.addJwtToCookie(loginResponse.token, response);
//		}
//		loginResponse.setToken(null);
//		// http 형태로 리턴. header에는 jwt가 있는 쿠키, body에 loginResponse.
//		return ResponseEntity.ok(loginResponse);
//	}
	
	@PostMapping("/signIn") // 로그인
	public ResponseEntity<?> signIn(@RequestBody MemberVO request, HttpServletResponse response) {
		LoginResponse loginResponse = service.signIn(request.getMemberId(), request.getMemberPw());
		log.info("컨트롤러에 찍히는 로그인 요청:"+loginResponse);
	    // Refresh Token을 HttpOnly 쿠키로 http header에 저장
	    ResponseCookie refreshCookie = 
	    		ResponseCookie.from("refreshToken", loginResponse.getRefreshToken())
	            .httpOnly(true)  // JS 접근 불가
	            .secure(true)    // HTTPS에서만
	            .path("/")       // 모든 경로에서 접근 가능
	            .maxAge(7 * 24 * 60 * 60) // 7일
	            .build();
	    response.addHeader("Set-Cookie", refreshCookie.toString());
	    
	    // Access Token만 body에 저장
	    return ResponseEntity.ok(Collections.singletonMap("accessToken", loginResponse.getAccessToken()));
	}
	
	@PostMapping("/refresh") // refreshToken으로 accesstoken 함수 활성화
	public ResponseEntity<?> refreshToken(HttpServletRequest request) {
	    String refreshToken = jwtProvider.extractTokenFromCookie(request, "refreshToken");

	    if (!jwtProvider.validateToken(refreshToken)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    String memberId = jwtProvider.extractMemberId(refreshToken);
	    String newAccessToken = jwtProvider.createAccessToken(memberId, "ROLE_USER");

	    return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
	}
	
	@GetMapping("getMyInfo") // 마이 페이지 내 정보 요청. 
	public  MyInfoResponse getMyInfo(@RequestParam String memberId){
		return service.getMyInfo(memberId);
	}

	
	
}
