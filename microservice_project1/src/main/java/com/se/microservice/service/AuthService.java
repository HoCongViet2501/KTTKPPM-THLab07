package com.se.microservice.service;


import com.se.microservice.model.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(String username, String password) throws Exception;
}
