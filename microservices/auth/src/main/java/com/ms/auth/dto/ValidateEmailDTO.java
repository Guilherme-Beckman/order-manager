package com.ms.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ValidateEmailDTO(
	    @NotNull(message = "correlationalId must not be null") 
	    @NotBlank(message = "correlationalId must not be blank")  
	    String correlationalId,
	    @NotNull(message = "code must not be null") 
	    @NotBlank(message = "code must not be blank")  
		String emailCode
		) {

}
