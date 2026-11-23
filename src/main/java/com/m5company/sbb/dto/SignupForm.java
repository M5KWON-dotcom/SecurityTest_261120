package com.m5company.sbb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupForm {
	
	@NotBlank(message = "아이디는 필수 입력사항입니다.")
	@Size(min = 4, message = "아이디는 4자 이상이어야 합니다.")
	private String username;
	
	@NotBlank(message = "비밀번호는 필수 입력사항입니다.")
	@Size(min = 4, message = "비밀번호는 4자 이상이어야 합니다.")
	private String password1;
	
	@NotBlank(message = "비밀번호 확인은 필수 입력사항입니다.")	
	private String password2;
	
	@NotBlank(message = "이메일은 필수 입력사항입니다.")
	private String email;
	
}