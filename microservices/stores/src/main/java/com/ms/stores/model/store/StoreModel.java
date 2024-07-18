package com.ms.stores.model.store;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ms.stores.model.Role;

@Document(collection = "stores")
public class StoreModel {
	@Id
	private String id;
	private String name;
	private String email;
	private String password;
	private String addressId;
	private String phone;
	private String CNPJ;
	private List<String> opening_hours;
	private List<String> products;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private List<Role> roles;


	
	public StoreModel() {
		this.products = new ArrayList<>();
		this.opening_hours = new ArrayList<>();
		this.roles = new ArrayList<>();
		roles.add(Role.ROLE_STORE);
	}

	public StoreModel(String name, String email, String password, String addressId, String phone, String cNPJ,
			List<String> opening_hours, List<String> products) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.setAddressId(addressId);
		this.phone = phone;
		this.CNPJ = cNPJ;
		this.opening_hours = opening_hours;
		this.products = products;
		this.accountNonExpired = true; 
		this.accountNonLocked = true;
		this.credentialsNonExpired = true; 
		this.enabled = true; 
	}

	public StoreModel(StoreDTO storeDto) {
		this.name = storeDto.name();
		this.email = storeDto.email();
		this.password = storeDto.password();
		this.phone = storeDto.phone();
		this.CNPJ = storeDto.CNPJ();
		this.products = new ArrayList<>();
		this.opening_hours = new ArrayList<>();
		this.accountNonExpired = true; 
		this.accountNonLocked = true; 
		this.credentialsNonExpired = true;
		this.enabled = true; 
		this.roles = new ArrayList<>();
		roles.add(Role.ROLE_STORE);
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCNPJ() {
		return CNPJ;
	}

	public void setCNPJ(String cNPJ) {
		CNPJ = cNPJ;
	}

	public List<String> getOpening_hours() {
		return opening_hours;
	}

	public void setOpening_hours(List<String> opening_hours) {
		this.opening_hours = opening_hours;
	}

	public List<String> getProducts() {
		return products;
	}

	public void setProducts(List<String> products) {
		this.products = products;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getUsername() {
		return email;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
