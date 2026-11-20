package com.m5company.sbb.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.m5company.sbb.UserRepository;
import com.m5company.sbb.entity.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService{

	private final UserRepository userRepository;
	//userid -> username
	//id회원번호 -> 기본키
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		
		SiteUser user = userRepository.findByUsername(username);
	    	if(user ==null) {
	    		throw new UsernameNotFoundException("해당 회원정보를 찾을 수 없습니다.");
	    	}
		//로그인하려고 사용자가 입력한 아이디로 회원정보 db에서 찾아오기
		return User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.authorities(user.getRole().getValue())
				.build();
	}

}