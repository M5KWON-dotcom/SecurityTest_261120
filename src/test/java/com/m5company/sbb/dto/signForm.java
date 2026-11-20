package com.m5company.sbb.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class signForm {

	@NotBlank(message= "아이디는 필수 입력사항입니다.")
	@Size(min =4, message="아이디는 4자 이상이어야합니다.")
    private String username;
	@NotBlank(message= "비밀번호는 필수 입력사항입니다.")
	@Size(min =4, message="비밀번호는 4자 이상이어야합니다.")
	private String password;
	@NotBlank(message= "비밀번호는 필수 입력사항입니다.")
	private String email;
}
