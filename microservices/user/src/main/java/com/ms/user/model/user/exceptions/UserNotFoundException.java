package com.ms.user.model.user.exceptions;

public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserNotFoundException () {
		super("User not found in repository");
	}

}
