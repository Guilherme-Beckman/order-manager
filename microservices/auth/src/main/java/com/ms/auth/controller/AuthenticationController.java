package com.ms.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.auth.dto.EmailDTO;
import com.ms.auth.service.CustomUserDetailsService;
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	@Autowired
	CustomUserDetailsService detailsService;
	
@PostMapping("/login")
public ResponseEntity<UserDetails> teste(@RequestBody EmailDTO email){
	System.out.println(email.email());
	var user = this.detailsService.loadUserByUsername(email.email());
	return ResponseEntity.ok().body(user);
}
}
