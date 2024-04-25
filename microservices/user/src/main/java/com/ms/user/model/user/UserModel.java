package com.ms.user.model.user;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ms.user.model.adress.AdressModel;

@Document(collection = "userdb")
public class UserModel {
	@Id
	private UUID id;
	private String cpf;
	private String name;
	private String lastName;
	private String email;
	@DBRef
	private List<AdressModel> adress;
	
	public UserModel() {
	}
	public UserModel(UUID id, String cpf, String name, String lastName, String email, List<AdressModel> adress) {
		this.id = id;
		this.cpf = cpf;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.adress = adress;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<AdressModel> getAdress() {
		return adress;
	}
	public void setAdress(List<AdressModel> adress) {
		this.adress = adress;
	}
	
	
}
