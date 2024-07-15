package com.ms.auth.infra.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ms.auth.infra.security.customAuthentication.providers.StoreAuthenticationProvider;
import com.ms.auth.infra.security.customAuthentication.providers.UserAuthenticationProvider;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	   @Autowired
	    private UserAuthenticationProvider userAuthenticationProvider;

	    @Autowired
	    private StoreAuthenticationProvider storeAuthenticationProvider;


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		return httpSecurity

				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(autorize -> autorize.requestMatchers(
						"/register",
						"/login", 
						"/sendcode",
						"/validate", 
						"/inviteResetPassword", 
						"/newPassword", 
						"/register-store",
						"/login-store",
						"/products"
						).permitAll().anyRequest().authenticated())
				.build();

	}




	@Bean
	   public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(userAuthenticationProvider, storeAuthenticationProvider));
    }



	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	

}