package com.ms.auth.infra.security.custom_authentication.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ms.auth.infra.security.custom_authentication.tokens.UserAuthenticationToken;
import com.ms.auth.service.clients.CustomUserDetailsService;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		if (userDetails == null || !userDetails.getPassword().equals(password)) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
		return new UserAuthenticationToken(userDetails, password);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UserAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
