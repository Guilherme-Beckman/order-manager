package com.ms.auth.dto.clients;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ValidateEmailDTO(
		@NotNull(message = "code must not be null") @NotBlank(message = "code must not be blank") String emailCode) {

}
