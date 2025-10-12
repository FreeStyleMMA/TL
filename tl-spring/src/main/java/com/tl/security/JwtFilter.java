package com.tl.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider jwtUtil;

	@Autowired
	private CustomUserDetailsService userDetailsService;


	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String cookieName="refreshToken";
		// 요청 header의 쿠키에서 token 추출
		String token = jwtUtil.extractTokenFromCookie(request,cookieName);
//		System.out.println("Filter에 찍히는 token:"+token);
		// token에서 memberId 추출
		if(token != null) {
			String memberId = jwtUtil.extractMemberId(token);
//			System.out.println("extractMEmberId 동ㅈ작 확인:"+memberId);

			//memberId에 Authntication 세팅이 안되어있으면 userDetails 가져오기
			if (memberId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(memberId);
				System.out.println("Filter에 찍히는 userDetails:"+userDetails);

				if (jwtUtil.validateToken(token)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken
							(userDetails,null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);// 인증 정보 저장
				}
			}
		}
		filterChain.doFilter(request, response);
	}
}
