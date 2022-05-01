package com.example.baigecode.presentation.controller;

import com.example.baigecode.business.entity.Problem;
import com.example.baigecode.business.entity.Submission;
import com.example.baigecode.business.service.ProblemService;
import com.example.baigecode.business.service.SubmissionService;
import com.example.baigecode.business.service.UserService;
import org.buildobjects.process.ProcBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Objects;
import java.util.Optional;

@Controller
public class TaskController {

    private ProblemService problemService;
    private SubmissionService submissionService;

    @Autowired
    public TaskController(
        ProblemService problemService,
        SubmissionService submissionService
    ) {
        this.problemService = problemService;
        this.submissionService = submissionService;
    }

    @GetMapping("/problems")
    public String getTasksPage(Model model) {
        model.addAttribute("problems", problemService.getAllProblems());
        return "tasks";
    }

    @GetMapping("/problem/{id}")
    public String getProblemPage(Model model, @PathVariable Long id) {
        Optional<Problem> problem = problemService.findProblemById(id);

        if(problem.isEmpty()) {
            throw new IllegalStateException("Problem with this id is not present");
        }

        model.addAttribute("problem", problem.get());

        return "problem";

    }

    @GetMapping("/status")
    public String getSubmissionPage(Model model) {
        model.addAttribute("submissions", submissionService.getAllSubmissions());
        return "status";
    }

    @GetMapping("/submission/{id}")
    public String getSubmissionDetailsPage(Model model, @PathVariable Long id) {
        Optional<Submission> submission = submissionService.getSubmissionById(id);
        if(submission.isEmpty()) {
            throw new IllegalStateException("Submission with this id not found");
        } else {
            model.addAttribute("submission_time", submission.get().getSubmission_time());
            model.addAttribute("sourceCode", submission.get().getSourceCode());
            return "submissionDetail";
        }

    }
}
