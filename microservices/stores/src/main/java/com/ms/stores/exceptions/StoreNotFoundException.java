package com.ms.stores.exceptions;


public class StoreNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public StoreNotFoundException () {
		super("Store not found in repository");
	}

}
