package com.example.baigecode.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private Long problem_id;
    private Integer status;
    @Column(columnDefinition = "TEXT")
    private String sourceCode;
    private Integer compiler;
    private Timestamp submission_time;
    private Long executionTime;


}
