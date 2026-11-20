package com.m5company.sbb.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.m5company.sbb.UserRepository;
import com.m5company.sbb.entity.SiteUser;
import com.m5company.sbb.entity.userRole;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	//DI -> @Autowired 보다 추천하는 주입방법
	
	private final PasswordEncoder passwordEncoder;
	
	public SiteUser create(String username,String password,String email) {
		SiteUser siteUser = new SiteUser();
		siteUser.setUsername(username);
		siteUser.setEmail(email);
		siteUser.setPassword(passwordEncoder.encode(password));
		//비밀번호 평문으로 저장
		//비밀번호를 db에 넣기 전에 암호화를 한 후에 암호문으로 db저장
		siteUser.setRole(userRole.USER);
		//롤 권한->일반 (기본 회원권한은 일반회원으로 설정)
		//siteUser.setRole(userRole.ADMIN);
	    //롤 권한 ->관리자
		userRepository.save(siteUser);
		return siteUser;
	}
}
