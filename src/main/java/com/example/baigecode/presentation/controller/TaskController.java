package com.example.baigecode.presentation.controller;

import com.example.baigecode.business.entity.BaigeUser;
import com.example.baigecode.business.entity.Organization;
import com.example.baigecode.business.entity.Problem;
import com.example.baigecode.business.entity.Submission;
import com.example.baigecode.business.service.OrganizationService;
import com.example.baigecode.business.service.ProblemService;
import com.example.baigecode.business.service.SubmissionService;
import com.example.baigecode.business.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.buildobjects.process.ProcBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class TaskController {

    private final ProblemService problemService;
    private final SubmissionService submissionService;
    private final UserService userService;
    private final OrganizationService organizationService;

    @GetMapping("/problems")
    public String getTasksPage(Model model) {
        model.addAttribute("problems", problemService.getAllProblems());
        return "problems";
    }

    @GetMapping("/myProfile")
    public String getMyProfile(Principal principal, Model model) {
        if(principal == null) {
            return "notAuth";
        }
        Optional<BaigeUser> user = userService.getUserByUsername(principal.getName());
        if(user.isEmpty()) {
            return "userNotExists";
        }
        if(principal == null) {
            model.addAttribute("authenticated", false);
            model.addAttribute("pageOwner", false);
        } else {
            Optional<BaigeUser> loggedUser = userService.getUserByUsername(principal.getName());
            model.addAttribute("authenticated", true);
            model.addAttribute("loggedUsername", loggedUser.get().getUsername());
            if(Objects.equals(user.get().getUsername(), principal.getName())) {
                model.addAttribute("pageOwner", true);
            } else {
                model.addAttribute("pageOwner", false);
                if (loggedUser.get().getFriends().contains(user.get())) {
                    model.addAttribute("follower", true);
                } else {
                    model.addAttribute("follower", false);
                }
            }
        }
        Optional<Organization> organization = Optional.empty();
        if(user.get().getOrganization_id() != null) {
            organization = organizationService.getOrganizationById(user.get().getOrganization_id());
        }
        if(organization.isEmpty()) {
            model.addAttribute("hasOrganization", false);
        } else {
            model.addAttribute("hasOrganization", true);
            model.addAttribute("organizationName", organization.get().getName());
            model.addAttribute("organizationId", organization.get().getId());
        }
        model.addAttribute("avatar", user.get().getAvatar());

        model.addAttribute("totalProblems", user.get().getTotalProblems());
        model.addAttribute("hardProblems", user.get().getHardProblems());
        model.addAttribute("mediumProblems", user.get().getMediumProblems());
        model.addAttribute("easyProblems", user.get().getEasyProblems());

        if (principal == null){
            model.addAttribute("authenticated", false);
            model.addAttribute("pageOwner", false);
        } else {
            model.addAttribute("lastSubmissions", submissionService.getUserSubmissionsStatusDto(principal.getName()));
        }


        model.addAttribute("acceptance", user.get().getAcceptance());

        model.addAttribute("username", user.get().getUsername());
        return "profile";
    }

    @GetMapping("/problem/{id}")
    public String getProblemPage(Model model, @PathVariable Long id, Principal principal) {
        Optional<Problem> problem = problemService.findProblemById(id);
        if(problem.isEmpty()) {
            throw new IllegalStateException("Problem with this id is not present");
        }
        model.addAttribute("problem", problem.get());
        if(principal == null) {
            model.addAttribute("authenticated", false);
        } else {
            model.addAttribute("authenticated", true);
            Optional<BaigeUser> user = userService.getUserByUsername(principal.getName());
            if(user.isEmpty()) {
                throw new UsernameNotFoundException("User not found");
            }
            model.addAttribute("userId", user.get().getId());
        }
        return "codepage";
    }


    @GetMapping("/user/{username}")
    public String getUserPage(@PathVariable String username, Principal principal, Model model) {
        Optional<BaigeUser> user = userService.getUserByUsername(username);
        if(user.isEmpty()) {
            return "userNotExists";
        }
        if(principal == null) {
            model.addAttribute("authenticated", false);
            model.addAttribute("pageOwner", false);
        } else {
            Optional<BaigeUser> loggedUser = userService.getUserByUsername(principal.getName());
            model.addAttribute("authenticated", true);
            model.addAttribute("loggedUsername", loggedUser.get().getUsername());
            if(Objects.equals(user.get().getUsername(), principal.getName())) {
                model.addAttribute("pageOwner", true);
            } else {
                model.addAttribute("pageOwner", false);
                if (loggedUser.get().getFriends().contains(user.get())) {
                    model.addAttribute("follower", true);
                } else {
                    model.addAttribute("follower", false);
                }
            }
        }
        Optional<Organization> organization = Optional.empty();
        if(user.get().getOrganization_id() != null) {
            organization = organizationService.getOrganizationById(user.get().getOrganization_id());
        }
        if(organization.isEmpty()) {
            model.addAttribute("hasOrganization", false);
        } else {
            model.addAttribute("hasOrganization", true);
            model.addAttribute("organizationName", organization.get().getName());
            model.addAttribute("organizationId", organization.get().getId());
        }
        model.addAttribute("avatar", user.get().getAvatar());

        model.addAttribute("totalProblems", user.get().getTotalProblems());
        model.addAttribute("hardProblems", user.get().getHardProblems());
        model.addAttribute("mediumProblems", user.get().getMediumProblems());
        model.addAttribute("easyProblems", user.get().getEasyProblems());

        if (principal == null){
            model.addAttribute("authenticated", false);
            model.addAttribute("pageOwner", false);
        } else {
            model.addAttribute("lastSubmissions", submissionService.getUserSubmissionsStatusDto(principal.getName()));
        }


        model.addAttribute("acceptance", user.get().getAcceptance());

        model.addAttribute("username", user.get().getUsername());
        return "profile";
    }

    @GetMapping("/status")
    public String getSubmissionPage(Model model, Principal principal) {
        if(principal != null) {
            model.addAttribute("authenticated", true);
            model.addAttribute("userSubmissions", submissionService.getUserSubmissionsStatusDto(principal.getName()));
        } else {
            model.addAttribute("authenticated", false);
        }
        model.addAttribute("submissions", submissionService.getAllSubmissionsStatusDto());
        return "newstatus";
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

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
