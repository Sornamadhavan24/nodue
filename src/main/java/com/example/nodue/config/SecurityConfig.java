package com.example.nodue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login", "/auth/register").permitAll() // Allow login & register
                .requestMatchers("/student/**").permitAll() // Allow student routes
                .requestMatchers("/faculty/**").permitAll() // Allow faculty routes
                .requestMatchers("/hod/**").permitAll() // Allow hod routes
                
                .requestMatchers("/student/submitMarks").permitAll()
                

            
                .requestMatchers("/student/**").hasRole("STUDENT")
                .requestMatchers("/faculty/**").hasRole("FACULTY")  // Allow faculty users
                .requestMatchers("/hod/**").hasRole("HOD")
                .requestMatchers("/auth/login", "/student/submitMarks/**", "/student/status/**").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }
}
