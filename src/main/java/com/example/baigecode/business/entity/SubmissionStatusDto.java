package com.example.baigecode.business.entity;

import com.example.baigecode.business.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionStatusDto {
    private Long id;
    private String username;
    private Integer status;
    private Integer compiler;
    private Timestamp submission_time;
    private Long executionTime;
    private String problemName;

    public SubmissionStatusDto(Submission submission, String username, String problemName) {
        this.username = username;
        id = submission.getId();
        status = submission.getStatus();
        compiler = submission.getCompiler();
        submission_time = submission.getSubmission_time();
        executionTime = submission.getExecutionTime();
        this.problemName = problemName;
    }
}
