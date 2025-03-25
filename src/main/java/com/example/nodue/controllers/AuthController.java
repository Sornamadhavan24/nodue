package com.example.nodue.controllers;

import com.example.nodue.dto.LoginRequest;
import com.example.nodue.model.User;
import com.example.nodue.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:5500" ,allowCredentials = "true")
@RestController
@RequestMapping("/auth")
public class AuthController {   
    
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());
    
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            // ✅ Check if the role matches the expected one
            if (!user.getRole().equals(loginRequest.getRole())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Incorrect Role");
            }
    
            return ResponseEntity.ok("login success");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
    }
    
    


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            System.out.println("Registering user: " + user.getEmail());
            User savedUser = userService.register(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
        }
    }

}
