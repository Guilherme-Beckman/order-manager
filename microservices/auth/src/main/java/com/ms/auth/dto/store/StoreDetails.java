package com.ms.auth.dto.store;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class StoreDetails implements UserDetails{
	private String id;
	private String name;
	private String email;
	private String password;
	private List<String> addresses;
	private String phone;
	private String CNPJ;
	private List<String> opening_hours;
	private List<String> products;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private boolean isValid;

	public StoreDetails() {
	}

	public StoreDetails(String name, String email, String password, List<String> addresses, String phone, String cNPJ,
			List<String> opening_hours, List<String> products) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.addresses = addresses;
		this.phone = phone;
		this.CNPJ = cNPJ;
		this.opening_hours = opening_hours;
		this.products = products;
		this.accountNonExpired = true; // Assumindo conta ativa por padrão
		this.accountNonLocked = true; // Assumindo conta não bloqueada por padrão
		this.credentialsNonExpired = true; // Assumindo credenciais ativas por padrão
		this.enabled = true; // Assumindo usuário ativo por padrão
		this.isValid = false;
	}

	public StoreDetails(StoreDTO storeDto) {
		this.name = storeDto.name();
		this.email = storeDto.email();
		this.password = storeDto.password();
		this.addresses = new ArrayList<String>();
		this.phone = storeDto.phone();
		this.CNPJ = storeDto.CNPJ();
		this.opening_hours = new ArrayList<>();
		this.accountNonExpired = true; // Assumindo conta ativa por padrão
		this.accountNonLocked = true; // Assumindo conta não bloqueada por padrão
		this.credentialsNonExpired = true; // Assumindo credenciais ativas por padrão
		this.enabled = true; // Assumindo usuário ativo por padrão
		this.isValid = false;

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public List<String> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<String> addresses) {
		this.addresses = addresses;
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

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return getAuthorities();
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}
}
