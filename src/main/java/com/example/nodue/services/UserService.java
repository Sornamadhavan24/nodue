package com.example.nodue.services;

import com.example.nodue.model.User;
import com.example.nodue.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
            .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }

    public boolean authenticate(String email, String password) {
        return userRepository.findByEmail(email)
            .map(user -> user.getPassword().equals(password)) // Match plain text password
            .orElse(false);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User register(User user) {
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if (userExists) {
            throw new RuntimeException("User already exists!");
        }
        return userRepository.save(user);
    }
}
