package com.ms.auth.dto.clients;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthenticationDTO (@NotBlank(message = "login must not be blank") @NotNull (message = "login must not be null")String login, @NotBlank(message = "login must not be blank") @NotNull(message = "login must not be null") String password){

}
