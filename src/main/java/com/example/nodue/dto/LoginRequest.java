package com.example.nodue.dto; // Replace with your actual package name

public class LoginRequest {
    private String email;
    private String password;
    private String role;

    // Default Constructor
    public LoginRequest() {}

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {  // ✅ Add this getter
        return role;
    }

    public void setRole(String role) { // ✅ Add this setter
        this.role = role;
    }
}
