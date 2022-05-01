package com.example.baigecode.business.service;

import com.example.baigecode.business.entity.Problem;
import com.example.baigecode.business.entity.Submission;
import com.example.baigecode.persistance.repository.ProblemRepository;
import com.example.baigecode.persistance.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProblemService {
    private ProblemRepository problemRepo;

    @Autowired
    public ProblemService(ProblemRepository problemRepo, SubmissionRepository submissionRepo) {
        this.problemRepo = problemRepo;
    }


    public List<Problem> getAllProblems() {
        return problemRepo.findAll();
    }

    public Optional<Problem> findProblemById(Long id) {
        return problemRepo.findById(id);
    }


}
