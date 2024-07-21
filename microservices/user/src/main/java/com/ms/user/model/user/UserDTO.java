package com.ms.user.model.user;

import com.ms.user.model.address.AddressDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(
    @NotBlank String cpf,    
    @NotBlank String password,
    @NotBlank String name,   
    @NotBlank String lastName,  
    @Email @NotBlank String email,
    @NotBlank @NotNull String phone,
    @NotNull AddressDTO address  
    
) {}
