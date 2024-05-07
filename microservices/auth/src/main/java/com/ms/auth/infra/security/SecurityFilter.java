package com.ms.auth.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.http.SecurityHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ms.auth.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component 
public class SecurityFilter extends OncePerRequestFilter{
@Autowired
TokenService tokenService;
@Autowired
CustomUserDetailsService userDetailsService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var token = this.recoverToken(request);
		if (token!=null) {
			var email = tokenService.validadeToken(token);
			UserDetails user = this.userDetailsService.loadUserByUsername(email);
			if(user!=null) {
				var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}else {
				filterChain.doFilter(request, response);
			}
		}else {
			filterChain.doFilter(request, response);
		}
	}
	  private String recoverToken(HttpServletRequest request){
	        var authHeader = request.getHeader("Authorization");
	        if(authHeader == null) return null;
	        return authHeader.replace("Bearer ", "");
	    }

}