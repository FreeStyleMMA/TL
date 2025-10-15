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

	    String cookieName = "refreshToken";

	    // 요청 header의 쿠키에서 token 추출
	    String token = jwtUtil.extractTokenFromCookie(request, cookieName);

	    // token이 null이거나 빈 문자열이면 통과
	    if (token == null || token.isEmpty()) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    try {
	        // token에서 memberId 추출
	        String memberId = jwtUtil.extractMemberId(token);

	        // memberId 존재하고 인증이 안 되어 있으면 인증 처리
	        if (memberId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            UserDetails userDetails = userDetailsService.loadUserByUsername(memberId);

	            if (jwtUtil.validateToken(token)) {
	                UsernamePasswordAuthenticationToken authToken =
	                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(authToken);
	            }
	        }
	    } catch (Exception e) {
	        // 토큰이 잘못된 경우 로그만 남기고 통과
	        log.warn("Invalid JWT token: " + e.getMessage());
	    }

	    filterChain.doFilter(request, response);
	}
	}