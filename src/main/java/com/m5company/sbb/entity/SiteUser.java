package com.m5company.sbb.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SiteUser {
      @Id
      @GeneratedValue(strategy =GenerationType.IDENTITY)  //ai옵션설정
	  private Long id;
      private String username;
      private String password;
      private String email;
      
      //권한설정(admin,user)
      //private String role;
      @Enumerated(EnumType.ORDINAL)
      private userRole role;
      //role은 enum타입이므로 admin,user만 가질수잇음
}
