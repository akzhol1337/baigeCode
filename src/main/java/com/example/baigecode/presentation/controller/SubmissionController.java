package com.example.baigecode.presentation.controller;

import com.example.baigecode.business.entity.Submission;
import com.example.baigecode.business.service.SubmissionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.buildobjects.process.ProcBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.security.Principal;
import java.util.Objects;
import java.util.Queue;

@RestController
@RequiredArgsConstructor
public class SubmissionController {
    final private SubmissionService submissionService;

    @PostMapping("/submit")
    public ResponseEntity<?> addSubmission(@RequestBody Submission submission, Principal principal) throws JsonProcessingException {
//        if(principal == null) {
//            return ResponseEntity.badRequest().body("Please login first");
//        }
        submissionService.addSubmissionToQueue(submission);
        return ResponseEntity.ok("added wws");
    }

}
