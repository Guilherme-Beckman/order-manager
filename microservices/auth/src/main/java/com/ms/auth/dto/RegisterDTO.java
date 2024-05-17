package com.ms.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(
    @NotBlank String cpf,    
    @NotBlank String password,
    @NotBlank String name,   
    @NotBlank String lastName,  
    @Email @NotBlank String email,
    @NotNull AddressDTO address  
) {}
