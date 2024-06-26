package com.ms.api_gateway.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsDTO implements UserDetails {
    private static final long serialVersionUID = 1L;
	private String id;
    private String cpf;
    private String password;
    private String name;
    private String lastName;
    private String email;
    private List<String> address;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    @SuppressWarnings("unused")
	private String username;
    private boolean isValid;
    public UserDetailsDTO() {
    }



    public UserDetailsDTO(String id, String cpf, String password, String name, String lastName, String email,
			List<String> address, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired,
			boolean enabled, String username, boolean isValid) {
		this.id = id;
		this.cpf = cpf;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
		this.username = username;
		this.isValid = isValid;
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

    public void setAddress(List<String> address) {
        this.address = address;
    }

    @Override
    public String getUsername() {
        return email; 
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
	public void setUsername(String username) {
		this.username = username;
	}



	public boolean isValid() {
		return isValid;
	}



	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}



}
