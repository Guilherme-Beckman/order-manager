package com.ms.auth.infra.security.custom_authentication.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.ms.auth.infra.security.custom_authentication.tokens.UserAuthenticationToken;
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
        System.out.println("Iniciando a autenticação no provider");
        
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        
        System.out.println("UserDetails carregado");
        System.out.println("Password informado: " + password);
        System.out.println("Password do UserDetails: " + userDetails.getPassword());
        
        if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("Autenticação bem-sucedida");
            return new UserAuthenticationToken(userDetails, password);
        }
        
        System.out.println("Autenticação falhou");
     
        throw new BadCredentialsException("");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UserAuthenticationToken.class.isAssignableFrom(authentication);
    }


}
