package com.tl.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tl.dto.LoginDTO;
import com.tl.mapper.MemberMapper;

import lombok.Setter;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Setter(onMethod_ = @Autowired)
	public MemberMapper mapper;
	
	
	@Override
	 public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        LoginDTO user = mapper.findByMemberId(memberId);
        
        if(user == null) throw new UsernameNotFoundException("사용자를 찾을 수 없습니다");
        
        //권한 인증을 위한 role 정보 세팅
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getAuthorities());

        //Jwt Filter를 위한 User객체 세팅
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
    }
}

