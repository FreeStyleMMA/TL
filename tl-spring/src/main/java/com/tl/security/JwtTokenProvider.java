package com.tl.security;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtTokenProvider {

	private static final String SECRET_KEY = "my-super-secret-key-that-is-at-least-32-bytes-long";
	private static final long ACCESS_TOKEN_VALIDITY = 1000L * 60 * 30; // 유효기간 30분
	private static final long REFRESH_TOKEN_VALIDITY = 1000L * 60 * 60 * 24 * 14; // 14일

	//AccessToken 생성
	public String createAccessToken(String memberId, String role) {
		Date now = new Date();
		return Jwts.builder()
				.setSubject(memberId)
				.claim("role", role)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALIDITY))
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256).compact();
	}

	public String createRefreshToken(String memberId) { // refreshToken 생성
		return createToken(memberId, REFRESH_TOKEN_VALIDITY);
	}

	// 토큰 만드는 기본 형식
	public String createToken(String memberId, long validity) {

		Date now = new Date();
		return Jwts.builder()
				.setSubject(memberId)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + validity))
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256).compact();
	}

	// 토큰 유효성 검증.
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(Keys
					.hmacShaKeyFor(SECRET_KEY.getBytes()))
			.build()
			.parseClaimsJws(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}
	
	//토큰에서 memberId 추출. 
	public String extractMemberId(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(Keys
				.hmacShaKeyFor(SECRET_KEY.getBytes()))
				.build()
				.parseClaimsJws(token).getBody().getSubject();
	}
	//토큰에서 role 추출
	public String extractRole(String token) {
		return (String) Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
				.build()
				.parseClaimsJws(token).getBody().get("role");
	}

	//http요청에서 쿠키(refreshToken에서) 추출
	public String extractTokenFromCookie(HttpServletRequest request, String cookieName) {
		if (request.getCookies() == null) {
			return null;
		}
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals(cookieName)) {
				return cookie.getValue();
			}
		}
		return null;
	}
	//token에서 Expiration 추출
	public Date extractExpiration(String token) {
	    Claims claims = Jwts.parserBuilder()
	            .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
	            .build()
	            .parseClaimsJws(token)  // JWT 파싱
	            .getBody();
	    return claims.getExpiration();
	}
}
