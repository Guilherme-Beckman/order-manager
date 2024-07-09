package com.ms.auth.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ms.auth.infra.security.custom_authentication.providers.StoreAuthenticationProvider;
import com.ms.auth.infra.security.custom_authentication.providers.UserAuthenticationProvider;

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
				.authorizeHttpRequests(autorize -> autorize.requestMatchers("/register", "/login", "/sendcode",
						"/validate", "/inviteResetPassword", "/newPassword").permitAll().anyRequest().authenticated())
				.build();

	}
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.authenticationProvider(userAuthenticationProvider);
	        auth.authenticationProvider(storeAuthenticationProvider);
	    }

		@Bean
		public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
				throws Exception {
			return authenticationConfiguration.getAuthenticationManager();
		}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	

}