package com.ms.auth.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ms.auth.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  {
@Autowired
CustomUserDetailsService customUserDetailsService; 
@Autowired
SecurityFilter securityFilter;
	@Bean
	public SecurityFilterChain securityFilterChain( HttpSecurity httpSecurity) throws Exception {

	return httpSecurity
            
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            .authorizeHttpRequests(autorize -> autorize
            		.requestMatchers(HttpMethod.GET, "auth/login").permitAll()
            		.requestMatchers(HttpMethod.POST, "auth/login").permitAll()
            		.requestMatchers(HttpMethod.GET, "/register").permitAll()
            		.requestMatchers(HttpMethod.POST, "/register").permitAll()
    
            )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
   
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean 
	public AuthenticationManager authenticationManager (AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	protected void configureAuthenticationManagerBuilder(AuthenticationManagerBuilder authenticationManagerBuilder) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(customUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		authenticationManagerBuilder.authenticationProvider(provider);
	}
	

}