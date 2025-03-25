package com.example.nodue.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.nodue.model.Marks;
import com.example.nodue.repositories.MarksRepository;

import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/hod")
@CrossOrigin(origins = "http://localhost:5500")
public class HODController {

    @Autowired
    private MarksRepository marksRepository;

    // Fetch faculty-approved requests only
    @GetMapping("/requests")
    public ResponseEntity<List<Marks>> getFacultyApprovedRequests() {
        List<Marks> approvedRequests = marksRepository.findByStatus("Approved by Faculty please wait for hod approval");
        return ResponseEntity.ok(approvedRequests);
    }

    // HOD approves or rejects
    @PutMapping("/updateStatus/{marksId}")
    public ResponseEntity<String> updateHodApproval(@PathVariable Long marksId, @RequestParam String status) {
        Marks marks = marksRepository.findById(marksId)
                .orElseThrow(() -> new RuntimeException("Marks entry not found"));

        if (!marks.getStatus().equals("Approved by Faculty please wait for hod approval")) {
            return ResponseEntity.badRequest().body("Only faculty-approved requests can be processed by HOD");
        }

        if (status.equalsIgnoreCase("approve")) {
            marks.setStatus("Approved by HOD now u can download a pdf");
        } else if (status.equalsIgnoreCase("reject")) {
            marks.setStatus("Rejected by HOD");
        } else {
            return ResponseEntity.badRequest().body("Invalid status. Use 'approve' or 'reject'");
        }

        marksRepository.save(marks);
        return ResponseEntity.ok("Status updated to: " + marks.getStatus());
    }
}
