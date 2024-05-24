package com.ms.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.user.model.address.AddressDTO;
import com.ms.user.model.user.UserDTO;
import com.ms.user.model.user.UserModel;
import com.ms.user.service.UserService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	public UserService userService;
	
	@PostMapping
	public ResponseEntity<UserModel> insertUser(@RequestBody @Valid UserDTO userDTO) {
	UserModel savedUser=  this.userService.insertUser(userDTO);
	return ResponseEntity.ok().body(savedUser);
	}
	@GetMapping("/{id}")
	public ResponseEntity<UserModel> getUserById(@PathVariable String id){
	var findedUser= this.userService.getUserById(id);
	return ResponseEntity.ok().body(findedUser);
	}
	@PostMapping("/{userId}")
	public ResponseEntity<UserModel> addAddress(@PathVariable String userId, @RequestBody AddressDTO newAddress) throws Exception{
	var user = this.userService.addAdress(userId, newAddress);
	return ResponseEntity.ok().body(user);
	}
	/*@DeleteMapping("/del")
	public void deleteAll() {
		this.userService.deleteAll();
	}*/
	
	}
