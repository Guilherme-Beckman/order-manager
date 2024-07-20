package com.ms.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.user.infra.security.CryptoUtils;
import com.ms.user.infra.security.TokenService;
import com.ms.user.infra.security.UserCrypto;
import com.ms.user.model.address.AddressDTO;
import com.ms.user.model.address.AddressModel;
import com.ms.user.model.user.Role;
import com.ms.user.model.user.UserDTO;
import com.ms.user.model.user.UserModel;
import com.ms.user.model.user.UserPerfilDTO;
import com.ms.user.model.user.exceptions.UserNotFoundException;
import com.ms.user.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AddressService addressService;
	@Autowired
	private UserCrypto userCrypto;
	@Autowired
	private CryptoUtils cryptoUtils;
	@Autowired
	private TokenService tokenService;

	@Transactional
	public UserModel insertUser(UserDTO userDTO) {
		var encryptedUser = this.userCrypto.cryptoUserData(userDTO);
		UserModel newUser = new UserModel(encryptedUser);
		UserModel savedUser = this.userRepository.insert(newUser);
		AddressDTO address = userDTO.address();
		UserModel userWithAddress = addAdress(savedUser, address);
		return this.userRepository.save(userWithAddress);
	}

	@Transactional
	public UserModel addAdress(UserModel userModel, AddressDTO address) {
		if (!(address.getZipCode() == null)) {
			address = new AddressDTO(address.getZipCode());
		}
		address.setUserId(userModel.getId());
		AddressModel addedAddress = this.addressService.insertAddress(address);
		List<String> addressesSavedUser = userModel.getAddress();
		addressesSavedUser.add(addedAddress.getId());
		userModel.setAdress(addressesSavedUser);
		return userModel;
	}

	@Transactional
	public void addAdress(HttpServletRequest request, AddressDTO address) {
		var user = this.findUserByToken(request);
		String userId = user.getId();
		var findedUser = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
		if (address.getZipCode() != null) {
			address = new AddressDTO(address.getZipCode());
		}
		address.setUserId(userId);
		AddressModel addedAddress = this.addressService.insertAddress(address);
		List<String> addressesSavedUser = findedUser.getAddress();
		addressesSavedUser.add(addedAddress.getId());
		findedUser.setAdress(addressesSavedUser);
		this.userRepository.save(findedUser);
	}

	public UserModel getUserById(String id) {
		return this.userRepository.findById(id).orElseThrow();
	}

	public UserModel getUserByEmail(String email) {
		UserModel user = userRepository.findByEmail(email.replace("\"", " ").trim());
		System.out.println(user);
		return user;
	}

	public void validateUserEmail(String email) {
		var emailE = this.cryptoUtils.encrypt(email);
		var user = this.getUserByEmail(emailE);
		user.getRoles().remove(Role.ROLE_NON_VERIFIED_EMAIL);
		user.getRoles().add(Role.ROLE_VERIFIED_EMAIL);
		user.setRoles(user.getRoles());
		this.userRepository.save(user);

	}

	public UserPerfilDTO getUserPerfil(HttpServletRequest request) {
		var user = this.findUserByToken(request);
		var decryptedUserPerfil = this.userCrypto.decryptUserData(user);

		return decryptedUserPerfil;
	}

	private UserModel findUserByToken(HttpServletRequest request) {
		var token = this.tokenService.recoverToken(request);
		var userInfos = this.tokenService.getTokenInformations(token);
		var email = userInfos.getSubject();
		var encryptedEmail = this.cryptoUtils.encrypt(email);
		var user = this.getUserByEmail(encryptedEmail);
		return user;
	}

	public void changePassword(String email, String newPassword) {
		var user = this.getUserByEmail(this.cryptoUtils.encrypt(email));
		if (user == null) {
			return;
		}
		user.setPassword(newPassword);
		this.userRepository.save(user);
	}
}
