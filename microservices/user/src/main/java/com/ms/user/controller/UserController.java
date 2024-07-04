package com.ms.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.user.model.user.UserPerfilDTO;
import com.ms.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController

public class UserController {
	@Autowired
	private UserService userService;
	@GetMapping("/perfil")
	public ResponseEntity<UserPerfilDTO> getUserPerfil(HttpServletRequest request) {
		UserPerfilDTO userPerfil = this.userService.getUserPerfil(request);
		return ResponseEntity.ok().body(userPerfil);
	}
}
