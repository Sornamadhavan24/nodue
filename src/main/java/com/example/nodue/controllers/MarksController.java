package com.example.nodue.controllers;

import com.example.nodue.model.Marks;
import com.example.nodue.model.User;
import com.example.nodue.repositories.MarksRepository;
import com.example.nodue.repositories.UserRepository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/student")
public class MarksController {

    @Autowired
    private MarksRepository marksRepository;

    @Autowired
    private UserRepository userRepository;

    // Submit student marks
    @PostMapping("/submitMarks/{email}")
    public ResponseEntity<?> submitMarks(@PathVariable String email, @RequestBody Marks marks) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Student not found"));
    
        if (!"STUDENT".equals(user.getRole())) {
            return ResponseEntity.badRequest().body("Invalid role");
        }
    
        // Check if marks already exist
        Marks existingMarks = marksRepository.findByUser(user).orElse(null);
    
        if (existingMarks != null) {
            // Update existing marks
            existingMarks.setName(marks.getName());
            existingMarks.setRollNo(marks.getRollNo());
            existingMarks.setDepartment(marks.getDepartment());
            existingMarks.setStudentClass(marks.getStudentClass());
            
            existingMarks.setSubject1(marks.getSubject1());
            existingMarks.setSubject2(marks.getSubject2());
            existingMarks.setSubject3(marks.getSubject3());
            existingMarks.setSubject4(marks.getSubject4());
            existingMarks.setSubject5(marks.getSubject5());
            existingMarks.setAssignment1(marks.getAssignment1());
            existingMarks.setAssignment2(marks.getAssignment2());
            existingMarks.setStatus("submitted to faculty please wait for approval"); // Reset status
            
            marksRepository.save(existingMarks);
            return ResponseEntity.ok("Marks updated successfully");
        }
    
        // If marks do not exist, save new marks
        marks.setUser(user);
        marks.setStatus("marks submitted to faculty");
        marksRepository.save(marks);
        
        return ResponseEntity.ok("Marks submitted successfully");
    }
    
    
    
 // Student checks their request status
  
    @GetMapping("/checkStatus/{email}")
public ResponseEntity<?> checkStatus(@PathVariable String email) {
    // Retrieve the student by email
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Student not found"));

    if (!"STUDENT".equals(user.getRole())) {
        return ResponseEntity.badRequest().body("Invalid role");
    }

    // Retrieve marks associated with the student
    Marks marks = marksRepository.findByUser(user).orElse(null);

    if (marks == null) {
        return ResponseEntity.status(404).body("No marks record found for student");
    }

    // ✅ Fetch `name` from `Marks` instead of `User`
    Map<String, Object> response = new HashMap<>();
    response.put("name", marks.getName()); // ✅ Assuming `getName()` is in `Marks`
    response.put("rollNo", marks.getRollNo());
    response.put("department", marks.getDepartment());
    response.put("className", marks.getStudentClass());

    response.put("subject1", marks.getSubject1());
    response.put("subject2", marks.getSubject2());
    response.put("subject3", marks.getSubject3());
    response.put("subject4", marks.getSubject4());
    response.put("subject5", marks.getSubject5());

    response.put("assignment1", marks.getAssignment1());
    response.put("assignment2", marks.getAssignment2());

    response.put("status", marks.getStatus());

    return ResponseEntity.ok(response);
}



    
}    

