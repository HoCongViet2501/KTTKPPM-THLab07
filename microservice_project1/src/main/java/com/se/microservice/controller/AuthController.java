package com.se.microservice.controller;

import com.se.microservice.model.User;
import com.se.microservice.model.dto.LoginRequest;
import com.se.microservice.service.AuthService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    
    private final AuthService authService;
    
    private final RestTemplate restTemplate;
    
    @Autowired
    public AuthController(AuthService authService, RestTemplate restTemplate) {
        this.authService = authService;
        this.restTemplate = restTemplate;
    }
    
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) throws Exception {
        return ResponseEntity.accepted().body(authService.login(loginRequest.getUsername(), loginRequest.getPassword()));
    }
    
    @PostMapping("/microservice/register")
    public String registerCallEndPoint(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user.getUsername());
        jsonObject.put("password", user.getPassword());
        jsonObject.put("role", user.getRole().toString());
        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);
        return restTemplate.postForObject("http://localhost:8081/api/auth/register", request, String.class);
    }
    
}
