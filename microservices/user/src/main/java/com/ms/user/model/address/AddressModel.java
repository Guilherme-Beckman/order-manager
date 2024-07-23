package com.ms.user.model.address;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usersAdressDb")
public class AddressModel {
	@Id
	private String id;
	private String userId;
	private String street;
	private String number;
	private String complement;
	private String neighborhood;
	private String city;
	private String state;
	private String zipCode;

	public AddressModel() {
	}

	public AddressModel(String id, String userId, String street, String number, String complement, String neighborhood,
			String city, String state, String zipCode) {
		this.id = id;
		this.userId = userId;
		this.street = street;
		this.number = number;
		this.complement = complement;
		this.neighborhood = neighborhood;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}

	public AddressModel(AddressDTO addressDTO) {
		this.userId = addressDTO.getUserId();
		this.street = addressDTO.getStreet();
		this.number = addressDTO.getNumber();
		this.complement = addressDTO.getComplement();
		this.neighborhood = addressDTO.getNeighborhood();
		this.city = addressDTO.getCity();
		this.state = addressDTO.getState();
		this.zipCode = addressDTO.getZipCode();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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

}
