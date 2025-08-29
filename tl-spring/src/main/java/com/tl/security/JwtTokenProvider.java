package com.tl.security;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtTokenProvider {
	//key 생성
	private final String SECRET_KEY = "MySuperSecretKeyForJWTGeneration12345";
	//인증 유효 시간 설정
	private final long EXPIRATION = 1000 * 60 * 60;

	private Key getKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}
	
	//JWT 토큰 생성
	public String createToken(String memberId) {
		return Jwts.builder()
				.setSubject(memberId)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
				.signWith(getKey())
				.compact();
	}
	
	//JWT 토큰 유효성 검증
	 public boolean validateToken(String token, UserDetails memberDetails) {
	        String username = extractMemberId(token);
	        return (username.equals(memberDetails.getUsername()) && !isTokenExpired(token));
    }
	 
    //토큰 만료 설정
    private boolean isTokenExpired(String token) {
    	return Jwts.parserBuilder().setSigningKey(getKey())
    			.build().parseClaimsJws(token)
    			.getBody().getExpiration()
    			.before(new Date());
    }
    
    //토큰에서 memberId 추출
    public String extractMemberId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
