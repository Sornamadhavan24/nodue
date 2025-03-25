package com.example.nodue.controllers;

import com.example.nodue.model.Marks;
import com.example.nodue.repositories.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculty")
@CrossOrigin(origins = "http://localhost:5500")

public class FacultyController {

    @Autowired
    private MarksRepository marksRepository;

    // 1️⃣ Faculty fetches all student requests
    @GetMapping("/requests")
    public ResponseEntity<List<Marks>> getAllStudentRequests() {
        List<Marks> requests = marksRepository.findAll();
        return ResponseEntity.ok(requests);
    }

    // 2️⃣ Faculty approves or rejects a student's request
    @PutMapping("/updateStatus/{marksId}")
    public ResponseEntity<String> updateRequestStatus(@PathVariable Long marksId, @RequestParam String status) {
        Marks marks = marksRepository.findById(marksId)
                .orElseThrow(() -> new RuntimeException("Marks entry not found"));

        if (status.equalsIgnoreCase("approve")) {
            marks.setStatus("Approved by Faculty please wait for hod approval");
        } else if (status.equalsIgnoreCase("reject")) {
            marks.setStatus("Rejected by Faculty");
        } else {
            return ResponseEntity.badRequest().body("Invalid status. Use 'approve' or 'reject'");
        }

        marksRepository.save(marks);
        return ResponseEntity.ok("Status updated to: " + marks.getStatus());
    }
}
