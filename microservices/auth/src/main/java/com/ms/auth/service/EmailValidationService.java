package com.ms.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.auth.dto.ValidateEmailDTO;
import com.ms.auth.infra.security.TokenService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class EmailValidationService {
	@Autowired
	private TokenService tokenService;
    @Autowired
    private UserService userService;
    
	public String generateCode(HttpServletRequest request){
		var token = this.tokenService.recoverToken(request);
		var email = this.tokenService.validateToken(token);
		var user = this.userService.loadUserByUsername(email);
		if (user.)
		return token;
	}
	public Object validateEmail(ValidateEmailDTO emailCodeDTO) {
		
		return null;
	}
}
