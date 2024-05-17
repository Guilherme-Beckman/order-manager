package com.ms.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ms.auth.dto.AuthenticationDTO;
import com.ms.auth.dto.LoginResponseDTO;
import com.ms.auth.dto.RegisterDTO;
import com.ms.auth.dto.UserDTO;
import com.ms.auth.dto.UserDetailsDTO;
import com.ms.auth.infra.security.TokenService;
import com.ms.auth.service.CustomUserDetailsService;
import com.ms.auth.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Autowired
    UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        try {
            System.out.println("Attempting to login with email: " + data.login());

            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            System.out.println("UsernamePasswordAuthenticationToken created: " + usernamePassword.toString());

            var auth = this.authenticationManager.authenticate(usernamePassword);
            System.out.println("Authentication successful: " + auth.toString());

            var token = tokenService.generateToken((UserDetailsDTO) auth.getPrincipal());
            System.out.println("Token generated successfully: " + token.toString());

            System.out.println("Login successful.");
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (Exception e) {
            System.out.println("Exception occurred during login: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    
    @PostMapping("/register")
    public ResponseEntity<UserDetails> register(@RequestBody @Valid UserDTO data) throws JsonProcessingException{
        System.out.println("Attempting to register user with email: " + data.email());

        var user = userService.registerUser(data);
        if (user != null) {
            System.out.println("User registered successfully: " + user.getUsername());
            return ResponseEntity.ok().body(user);
        } else {
            System.out.println("Failed to register user.");
            return ResponseEntity.badRequest().build();
        }
    }
}
