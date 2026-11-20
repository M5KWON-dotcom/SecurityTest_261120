package com.m5company.sbb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class SecurityConfig {
	
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
		    .authorizeHttpRequests(auth -> auth //권한부여
		    
		    //.anyRequest().permitAll() 
		    //모든 페이지에 대한 요청을 허가 누구나 모든페이지에 접근가능
		    
		    //로그인페이지나 회원가입 페이지처럼 누구나 봐야할페이지만 접근가능하게 설정		
		    //admin요청 -> 관리자만 허가해야하는 요청
            .requestMatchers(  //허가해야 할 목록 작성
            		"/", //루트요청 허용(대문페이지 허용)
            		"/user/login", //로그인 요청 허용
            		"/user/signup", //회원가입 요청허용
            		"/css/**", //css파일이 허용되도록 등록
            		"/img/**",  //이미지파일이 허용되도록 등록
            		"/js/**", //자바스크립트 파일이 허용되도록등록
            		"/question/list",  //게시판리스트
            		"/qustion/detail/**" //게시판 내용보기
            		
            ).permitAll()
            .requestMatchers(  //허가해야 할 목록 작성
            		"/admin" //관리자 페이지 요청
            		).hasRole("ADMIN")            		            
            //위 요청들을 제외한 요청들은 모두 로그인 해야만 접근 가능 설정
            .anyRequest().authenticated()
            )
		    //로그인설정 -> 로그인하지 않으면 볼 수없는 요청이 들어왔을때
		    //loginpage 로 강제이동
		    .formLogin(login -> login
		    		.loginPage("/login") //로그인 페이지 등록
		    		
		            .defaultSuccessUrl("/",true) //로그인 성공했을때 이동하는 페이지 등록
		    		.permitAll()
		    		)
		            .logout(logout -> logout
		            		.logoutUrl("/logout")
		            		.logoutSuccessUrl("/") 
		            		.invalidateHttpSession(true) //세션초기화 세션에 올라와있는 로그인정보 삭제
		            		.deleteCookies("JSESSIONID")//쿠키삭제
		            );
		            
		    return http.build();
	}
}
