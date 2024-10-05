package com.spring.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.AuthRequest;
import com.spring.dto.AuthResponse;
import com.spring.dto.UserInfoDetails;
import com.spring.entity.UserInfo;
import com.spring.service.JwtService;
import com.spring.service.UserInfoService;

@RestController
@RequestMapping("/api")
public class HomeController {


	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserInfoService userInfoService;

	@GetMapping("/home")
	public ResponseEntity<String> home() {
		return new ResponseEntity<String>("home", HttpStatus.OK);
	}

	@GetMapping("/admin")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> admin() {
		return new ResponseEntity<String>("admin", HttpStatus.OK);
	}

	@GetMapping("/user")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<String> user() {
		return new ResponseEntity<String>("user", HttpStatus.OK);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<String> createUser(@RequestBody UserInfo userInfo){
		this.userInfoService.addUser(userInfo);
		return new ResponseEntity<String>("User created", HttpStatus.OK);
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest authRequest) {
		Authentication authentication;

		try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }
		
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserInfoDetails userInfoDetail= (UserInfoDetails) authentication.getPrincipal();        
        String username = userInfoDetail.getUsername();

        String jwtToken = jwtService.generateToken(username);
        
        UserInfo userInfo = this.userInfoService.getUserByUsername(username);
        AuthResponse authResponse = new AuthResponse(userInfo);
        authResponse.setToken(jwtToken);
		
	    return new ResponseEntity<Object>(authResponse, HttpStatus.OK);
        
	}

}
