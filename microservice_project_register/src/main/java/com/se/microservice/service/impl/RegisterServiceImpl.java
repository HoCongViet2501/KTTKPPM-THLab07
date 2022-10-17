package com.se.microservice.service.impl;

import com.se.microservice.model.User;
import com.se.microservice.repository.UserRepository;
import com.se.microservice.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {
    
    private final UserRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    RegisterServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }
}
