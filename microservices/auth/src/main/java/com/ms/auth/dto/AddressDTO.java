package com.ms.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddressDTO {

	private String userId;
	private String street;
	private String number;
	private String complement;
	private String neighborhood;
	private String city;
	private String state;
	private String zipCode;

	public AddressDTO() {
	}

	public AddressDTO(
			@NotNull(message = "Street must not be null") @NotBlank(message = "Street must not be blank") String street,
			@NotNull(message = "Number must not be null") @NotBlank(message = "Number must not be blank") String number,
			String complement,
			@NotNull(message = "Neighborhood must not be null") @NotBlank(message = "Neighborhood must not be blank") String neighborhood,
			@NotNull(message = "City must not be null") @NotBlank(message = "City must not be blank") String city,
			@NotNull(message = "State must not be null") @NotBlank(message = "State must not be blank") String state) {
		this.street = street;
		this.number = number;
		this.complement = complement;
		this.neighborhood = neighborhood;
		this.city = city;
		this.state = state;
	}

	public AddressDTO(@NotNull(message = "ZipCode must not be null") @NotBlank(message = "ZipCode must not be blank") String zipCode) {
		this.zipCode = zipCode;
	}

	// Getters and Setters
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
