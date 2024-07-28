package com.ms.auth.dto.clients;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ValidateEmailDTO(
		@NotBlank(message = "code must not be blank") @NotNull(message = "code must not be null") String emailCode) {
}