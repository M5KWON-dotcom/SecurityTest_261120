package com.m5company.sbb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.m5company.sbb.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;

	UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/signup")
	public String signup(SignupForm signupForm) {
		 
		return "signup_form";
	}
	
	@PostMapping("/signup") 
	public String signup(@Valid SignupForm signupForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) { //참->유효성체크 검사 실패
			return "signup_form";
		}
		
		//비밀번호 확인 부분 추가
		//참이면 비밀번호와 비밀번호 확인이 일치하지 않을 때 에러를 발생
		if(!signupForm.getPassword1().equals(signupForm.getPassword2())) {
			
			bindingResult.rejectValue("password2", "passwordInCorrect", 
					"비밀번호 확인이 일치하지 않습니다.");
			
			return "signup_form";
		}
		
		//유효성 체크 통과 시 DB에 저장하는 서비스 호출
		userService.create(
				signupForm.getUsername(), 
				signupForm.getPassword1(), 
				signupForm.getEmail());
		
		return "redirect:/user/login"; //회원 가입 성공 후 로그인 화면으로 강제 이동
		
	}
	
	@GetMapping("/login")
	public String login() {
		return "login_form";
	}

}