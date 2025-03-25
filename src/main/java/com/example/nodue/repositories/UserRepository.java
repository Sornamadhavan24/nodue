package com.example.nodue.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.nodue.model.User;

    @Repository
    public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByEmailAndPassword(String email, String password);
        Optional<User> findByEmail(String email);  // Change return type to Optional<User>
    }
    
