package com.tl.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tl.dto.MemberVO;
import com.tl.mapper.MemberMapper;

import lombok.Setter;

public class CustomUserDetailsService implements UserDetailsService{

	@Setter(onMethod_ = @Autowired)
	public MemberMapper mapper;
	
	
	@Override
	 public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        MemberVO user = mapper.findByMemberId(memberId);
        if(user == null) throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
        
        //권한 인증을 위한 role 정보 세팅
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());

        //jwt 토큰 생성을 위한 User(member) 정보 세팅
        return new org.springframework.security.core.userdetails.User(
                user.getMemberId(),
                user.getMemberPw(),
                Collections.singletonList(authority)
        );
    }
}

