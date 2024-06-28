package com.ms.api_gateway.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.ms.api_gateway.service.ReactiveCustomUserDetailsService;

import reactor.core.publisher.Mono;

@Component
public class JWTAuthenticationFilter implements WebFilter {
	@Autowired
	private TokenService tokenService;
	@Autowired
	private ReactiveCustomUserDetailsService reactiveCustomUserDetailsService;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

		if (authHeader != null && authHeader.startsWith("Bearer")) {
			String token = authHeader.substring(7);

			String email = this.tokenService.validateToken(token);

			if (email != null) {
				return reactiveCustomUserDetailsService.loadUserByUsername(email).flatMap(userDetails -> {
					SecurityContextImpl context = new SecurityContextImpl(
							new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
					return chain.filter(exchange)
							.contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
				});
			}
		}

		return chain.filter(exchange);
	}

}