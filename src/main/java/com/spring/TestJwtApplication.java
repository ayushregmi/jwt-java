package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.dto.UserInfoDetails;
import com.spring.entity.UserInfo;
import com.spring.service.JwtService;

@SpringBootApplication
public class TestJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestJwtApplication.class, args);
		
//		UserInfo userInfo = new UserInfo();
//		userInfo.setName("aayush");
//		userInfo.setRoles("ROLE_USER");
//		
//		UserInfoDetails userInfoDetails = new UserInfoDetails(userInfo);
//		
//		JwtService jwtService = new JwtService();
//		String token = jwtService.generateToken(userInfoDetails);
//		System.out.println("Token: "+ token);
//		
//		
//		
//		System.out.println(jwtService.extractAllClaims(token));
//		
	}

}
