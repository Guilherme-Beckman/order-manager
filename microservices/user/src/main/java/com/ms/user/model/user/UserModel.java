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
	private String phone;
	private List<String> address;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private List<Role> roles;

	public UserModel() {
		this.address = new ArrayList<String>();
		this.roles = new ArrayList<>();
		roles.add(Role.ROLE_USER);
		roles.add(Role.ROLE_NON_VERIFIED_EMAIL);
	}
	public UserModel(UserDTO userDTO) {
		this.cpf = userDTO.cpf();
		this.password = userDTO.password();
		this.name = userDTO.name();
		this.lastName = userDTO.lastName();
		this.email = userDTO.email();
		this.setPhone(userDTO.phone());
		this.address = new ArrayList<String>();
		this.accountNonExpired = true; 
		this.accountNonLocked = true; 
		this.credentialsNonExpired = true; 
		this.enabled = true; 
		this.roles = new ArrayList<>();
		roles.add(Role.ROLE_USER);
		roles.add(Role.ROLE_NON_VERIFIED_EMAIL);
	}

	public UserModel(String id, String cpf, String password, String name, String lastName, String email, String phone) {
		this.id = id;
		this.cpf = cpf;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		
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


	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

}
