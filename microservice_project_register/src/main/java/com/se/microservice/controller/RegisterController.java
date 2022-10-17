package com.se.microservice.controller;

import com.se.microservice.model.User;
import com.se.microservice.model.dto.LoginRequest;
import com.se.microservice.service.RegisterService;
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
public class RegisterController {
    private final RegisterService registerService;
    private final RestTemplate restTemplate;
    
    @Autowired
    public RegisterController(RegisterService registerService, RestTemplate restTemplate) {
        this.registerService = registerService;
        this.restTemplate = restTemplate;
    }
    
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User user) {
        return ResponseEntity.ok().body(this.registerService.register(user));
    }
    
    @PostMapping("/microservice/login")
    public String loginCallEndPoint(@RequestBody LoginRequest loginRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", loginRequest.getUsername());
        jsonObject.put("password", loginRequest.getPassword());
        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), headers);
        return restTemplate.postForObject("http://localhost:8080/api/auth/login", request, String.class);
    }
}
