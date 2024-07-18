package com.ms.auth.infra.security.customAuthentication.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.ms.auth.infra.security.customAuthentication.tokens.StoreAuthenticationToken;
import com.ms.auth.infra.security.customAuthentication.tokens.UserAuthenticationToken;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    public Authentication authenticateUser(String username, String password) throws AuthenticationException {
        UserAuthenticationToken authRequest = new UserAuthenticationToken(username, password);
        return authenticationManager.authenticate(authRequest);
    }

    public Authentication authenticateStore(String username, String password) throws AuthenticationException {
        StoreAuthenticationToken authRequest = new StoreAuthenticationToken(username, password);
        return authenticationManager.authenticate(authRequest);
    }
}
