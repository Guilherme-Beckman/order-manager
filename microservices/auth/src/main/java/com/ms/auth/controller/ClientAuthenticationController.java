package com.ms.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.auth.dto.clients.AuthenticationDTO;
import com.ms.auth.dto.clients.LoginResponseDTO;
import com.ms.auth.dto.clients.UserDTO;
import com.ms.auth.dto.clients.ValidateEmailDTO;
import com.ms.auth.infra.security.TokenService;
import com.ms.auth.service.clients.EmailService;
import com.ms.auth.service.clients.ResetPasswordService;
import com.ms.auth.service.clients.UserAuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RestController
public class ClientAuthenticationController {

	@Autowired
	private UserAuthenticationService userService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ResetPasswordService resetPasswordService;
	@Autowired
	private TokenService tokenService;

	@PostMapping("/register")
	public ResponseEntity<UserDetails> register(@RequestBody @Valid UserDTO data) {
		var user = this.userService.registerUser(data);
		return ResponseEntity.ok().body(user);

	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
		System.out.println("um usuario esta tentando logar controller");
		var token = this.userService.userLogin(data);
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}

	@GetMapping("/sendcode")
	public void sendCode(HttpServletRequest request) {
		var token = this.tokenService.recoverToken(request);
		this.emailService.sendCode(token);
	}

	@PostMapping("/validate")
	public ResponseEntity<String> validate(@Valid @RequestBody ValidateEmailDTO emailCodeDTO,
			HttpServletRequest request) {
		var message = this.emailService.validateEmailCode(emailCodeDTO, request);
		return ResponseEntity.ok().body(message);

	}

	@GetMapping("/inviteResetPassword")
	public void inviteResetPasswordEmail(@RequestBody @NotBlank @NotNull String email) {
		this.resetPasswordService.inviteResetPasswordEmail(email);
	}

	@PostMapping("/newPassword")
	public ResponseEntity<String> newPassword(@RequestParam("email") String email, @RequestParam("token") String token,
			@RequestBody String newPassword) {
		var message = this.resetPasswordService.validateEmailCode(email, token, newPassword);
		return ResponseEntity.ok().body(message);

	}

}
