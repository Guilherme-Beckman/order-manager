package com.ms.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.user.infra.security.UserCrypto;
import com.ms.user.model.address.AddressDTO;
import com.ms.user.model.address.AddressModel;
import com.ms.user.model.user.UserDTO;
import com.ms.user.model.user.UserModel;
import com.ms.user.model.user.exceptions.UserNotFoundException;
import com.ms.user.repository.AddressRepository;
import com.ms.user.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public AddressRepository address;
    @Autowired
    public AddressService addressService;
    @Autowired
    public UserCrypto userCrypto;
    @Transactional
    public UserModel insertUser(UserDTO userDTO) throws Exception {
    	var encryptedUser = this.userCrypto.cryptoUserData(userDTO);
        UserModel newUser = new UserModel(encryptedUser);
        UserModel savedUser = this.userRepository.insert(newUser);
        AddressDTO address =  userDTO.address();
        return addAdress(savedUser.getId(), address);
        

    }
    @Transactional
    public UserModel addAdress(String userId, AddressDTO address) throws Exception { 
    	var findedUser = this.userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    	 if (!(address.getZipCode() ==null)) {
             address = new AddressDTO(address.getZipCode());
         } 
        address.setUserId(userId);
        AddressModel addedAddress = this.addressService.insertAddress(address);
        List<String> addressesSavedUser = findedUser.getAddress();
        addressesSavedUser.add(addedAddress.getId());
        findedUser.setAdress(addressesSavedUser);
        return this.userRepository.save(findedUser);
    }
    public UserModel getUserById(String id) {
    	return this.userRepository.findById(id).orElseThrow();
    }
    
    public UserModel getUserByEmail(String email){
    	return this.userRepository.findByEmail(email);
    }
   /* public void deleteAll() {
    	this.userRepository.deleteAll();
    	this.address.deleteAll();
    	
    }*/
    }


