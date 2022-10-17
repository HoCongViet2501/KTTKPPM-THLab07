package com.se.microservice.service.impl;

import com.se.microservice.model.dto.LoginResponse;
import com.se.microservice.security.jwt.JwtProvider;
import com.se.microservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    
    private final AuthenticationManager authenticationManager;
    
    private final JwtProvider jwtProvider;
    
    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }
    @Override
    public LoginResponse login(String username, String password) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            String token = jwtProvider.createToken(username, String.valueOf(role));
            return new LoginResponse(username, token, role); 
        }catch (AuthenticationException e){
            e.printStackTrace();
            throw new Exception("username or password is incorrect!");
        }
        
    }
}
