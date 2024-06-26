package com.ms.user.model.user;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userdb")
public class UserModel {
	@Id
	private String id;
	private String cpf;
	private String password;
	private String name;
	private String lastName;
	@Indexed(unique = true)
	private String email;
	private List<String> address;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private boolean isValid;

	public UserModel() {
		this.address = new ArrayList<String>();

	}

	public UserModel(UserDTO userDTO) {
		this.cpf = userDTO.cpf();
		this.password = userDTO.password();
		this.name = userDTO.name();
		this.lastName = userDTO.lastName();
		this.email = userDTO.email();
		this.address = new ArrayList<String>();
		this.address = new ArrayList<>();
		this.accountNonExpired = true; // Assumindo conta ativa por padrão
		this.accountNonLocked = true; // Assumindo conta não bloqueada por padrão
		this.credentialsNonExpired = true; // Assumindo credenciais ativas por padrão
		this.enabled = true; // Assumindo usuário ativo por padrão
	}

	public UserModel(String id, String cpf, String password, String name, String lastName, String email) {
		this.id = id;
		this.cpf = cpf;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.email = email;

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

	public List<String> getAddress() {
		return address;
	}

	public void setAdress(List<String> address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return email;
	}

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return credentialsNonExpired;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

}
