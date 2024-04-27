package com.ms.user.model.address;

import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.ms.user.service.AddressService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;

public class AddressDTO {

	
    private String userId;
    @NotBlank 
    private String street;
    @NotBlank 
    private String number;
    private String complement;
    @NotBlank 
    private String neighborhood;
    @NotBlank 
    private String city;
    @NotBlank 
    private String state;
    private String zipCode;
    
    
	public AddressDTO() {
	}

	public AddressDTO(@NotBlank String street, @NotBlank String number, String complement,
			@NotBlank String neighborhood, @NotBlank String city, @NotBlank String state) {
		this.street = street;
		this.number = number;
		this.complement = complement;
		this.neighborhood = neighborhood;
		this.city = city;
		this.state = state;
	}

	public AddressDTO(@NotBlank String zipCode) {
		AddressService addressService = new AddressService(); 
	    AddressDTO dto = addressService.insertAddressByCPF(zipCode); 
	    this.street = dto.getStreet(); 
	    this.number = dto.getNumber(); 
	    this.complement = dto.getComplement(); 
	    this.neighborhood = dto.getNeighborhood(); 
	    this.city = dto.getCity(); 
	    this.state = dto.getState(); 
	    this.zipCode = zipCode;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
    
    
    
    
}
