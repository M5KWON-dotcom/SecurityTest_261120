package com.m5company.sbb.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

//enum은 정해진 값만 사용 할 수있도록 
@AllArgsConstructor
@Getter
public enum userRole {

	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");
	
private final String value;
}
