package com.ms.user.model.user.exceptions;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException () {
		super("User not found in repository");
	}

}
