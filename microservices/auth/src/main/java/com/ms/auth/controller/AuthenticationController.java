package com.ms.auth.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.auth.dto.AuthenticationDTO;
import com.ms.auth.dto.LoginResponseDTO;
import com.ms.auth.dto.UserDTO;
import com.ms.auth.dto.ValidateEmailDTO;
import com.ms.auth.service.EmailValidationService;
import com.ms.auth.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;
    @Autowired 
    EmailValidationService emailValidationService;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data){
        	var token  = this.userService.userLogin(data);
        	return ResponseEntity.ok(new LoginResponseDTO(token));
    }
    
    @PostMapping("/register")
    public ResponseEntity<UserDetails> register(@RequestBody @Valid UserDTO data) {
        var user = this.userService.registerUser(data);
        return ResponseEntity.ok().body(user);
        
    }
    @PostMapping("/sendcode")
    public ResponseEntity<String> sendCode(HttpServletRequest request){
    	String result = this.emailValidationService.sendCode(request);
		return ResponseEntity.ok().body(result);
    }
    @PostMapping("/validate")
    public ResponseEntity<String> validate(@Valid @RequestBody ValidateEmailDTO emailCodeDTO, HttpServletRequest request) {
    	var message = this.emailValidationService.validateEmailCode(emailCodeDTO, request);
        return ResponseEntity.ok().body(message);
        
    }
    
}
