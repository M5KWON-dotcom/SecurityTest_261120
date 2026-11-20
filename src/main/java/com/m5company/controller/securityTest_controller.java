package com.m5company.controller;

import com.m5company.sbb.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class securityTest_controller {

	private final UserService userService;
	securityTest_controller(UserService userService) {
		this.userService = userService;
	}
	@GetMapping("/index")
	public String root(){
		return "index";
	}
	@GetMapping("/admin")
	public String admin(){
		return "admin";
	}
	@GetMapping("/userinfo")
	public String userinfo(){
		return "userinfo";
	}
	
	@GetMapping("/login")
	public String login(){
		return "login";
	}
	@GetMapping("/signup")
	public String signup(){
		return "signip";
	}
	@PostMapping("/signup")
	public String signup(
	        @RequestParam("username")String username,
			@RequestParam("password")String password,
		    @RequestParam("email")String email
		    ) { 
	        userService.create(username,password,email);
		     
			 return "redirect:/login";
	        }	
}
