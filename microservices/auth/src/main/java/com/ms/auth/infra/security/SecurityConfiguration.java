package com.ms.auth.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  {

	@Bean
	public SecurityFilterChain securityFilterChain( HttpSecurity httpSecurity) throws Exception {
		 //System.out.println("só para entender onde passa");
	return httpSecurity
            
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            .authorizeHttpRequests(autorize -> autorize
            		.requestMatchers(HttpMethod.GET, "auth/login").permitAll()
            		.requestMatchers(HttpMethod.POST, "auth/login").permitAll()
            		.requestMatchers(HttpMethod.GET, "/register").permitAll()
            		.requestMatchers(HttpMethod.POST, "/register").permitAll()
    
            )
            .build();
   
	}

}