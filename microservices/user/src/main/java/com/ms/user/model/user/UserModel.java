package com.ms.user.model.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "userdb")
public class UserModel {
	@Id
	private String id;
	private String cpf;
	private String name;
	private String lastName;
	private String email;
	private List<String> adress;
	
	public UserModel() {
	}
	public UserModel(UserDTO userDTO) {
		this.cpf = userDTO.cpf();
		this.name = userDTO.name();
		this.lastName = userDTO.lastName();
		this.email = userDTO.email();
		this.adress = new ArrayList<String>();
		
	}
	public UserModel(String id, String cpf, String name, String lastName, String email) {
		this.id = id;
		this.cpf = cpf;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.adress = new ArrayList<String>();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public List<String> getAdress() {
		return adress;
	}
	public void setAdress(List<String> adress) {
		this.adress = adress;
	}
	
	
}
