package com.ms.apiGateway.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

	@Autowired
	private JWTAuthenticationFilter authenticationFilter;

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http.csrf(csrf -> csrf.disable())
				.authorizeExchange(exchange -> exchange
						.pathMatchers("/auth/login", "/auth/register", "/auth/register-store", "/auth/login-store",
								"/products/all", "/products/id/**", "/stores/perfil/**")
						.permitAll().pathMatchers("/stores/addProduct", "/stores/menu/**").hasRole("STORE")
						.pathMatchers("/auth/inviteResetPassword", "/auth/newPassword","products/review/**").hasRole("USER")
						.pathMatchers("/auth/sendcode", "/auth/validate").hasRole("NON_VERIFIED_EMAIL")
						.pathMatchers("/clients/addAddress", "/clients/perfil").hasRole("VERIFIED_EMAIL")
						.anyExchange()
						.authenticated())
				.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
				.addFilterBefore(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION).build();
	}
}
