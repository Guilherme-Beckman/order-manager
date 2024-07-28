package com.ms.auth.dto.clients;

import com.ms.auth.dto.address.AddressDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(
        @NotNull(message = "cpf must not be null") @NotBlank(message = "cpf must not be blank") String cpf,

        @NotNull(message = "password must not be null") @NotBlank(message = "password must not be blank") String password,

        @NotNull(message = "name must not be null") @NotBlank(message = "name must not be blank") String name,

        @NotNull(message = "lastName must not be null") @NotBlank(message = "lastName must not be blank") String lastName,

        @NotNull(message = "email must not be null") @Email(message = "email must be a valid email address") @NotBlank(message = "email must not be blank") String email,

        @NotNull(message = "phone must not be null") @NotBlank(message = "phone must not be blank") String phone,

        @NotNull(message = "address must not be null") AddressDTO address) {
}
