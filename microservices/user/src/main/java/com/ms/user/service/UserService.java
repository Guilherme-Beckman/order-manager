package com.ms.user.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.user.model.address.AddressDTO;
import com.ms.user.model.address.AddressModel;
import com.ms.user.model.user.UserDTO;
import com.ms.user.model.user.UserModel;
import com.ms.user.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public AddressService addressService;
    @Transactional
    public UserModel insertUser(UserDTO userDTO) {
        UserModel newUser = new UserModel(userDTO);
        UserModel savedUser = this.userRepository.insert(newUser);
        AddressDTO address;
        if (!(userDTO.address().getZipCode()==null)) {
            address = new AddressDTO(userDTO.address().getZipCode());
        } else {
            address = userDTO.address();

        }
        address.setUserId(savedUser.getId());
        AddressModel addedAddress = this.addressService.insertAddress(address);
        List<String> addressesSavedUser = savedUser.getAdress();
        addressesSavedUser.add(addedAddress.getId());
        savedUser.setAdress(addressesSavedUser);
        return this.userRepository.save(savedUser);

    }
    public UserModel getUserById(String id) {
    	return this.userRepository.findById(id).orElseThrow();
    }
		
    }


