package com.m5company.sbb;

import org.springframework.data.jpa.repository.JpaRepository;

import com.m5company.sbb.entity.SiteUser;
import java.util.List;



public interface UserRepository extends JpaRepository<SiteUser, Long>{

	SiteUser findByUsername(String username);


}
