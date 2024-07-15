package com.ms.auth.infra.security.customAuthentication.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ms.auth.infra.security.customAuthentication.tokens.UserAuthenticationToken;
import com.ms.auth.service.clients.CustomUserDetailsService;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	@Lazy
	private CustomUserDetailsService userDetailsService;

	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword())) {
			return new UserAuthenticationToken(userDetails, password);
		}

		throw new BadCredentialsException("");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UserAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
