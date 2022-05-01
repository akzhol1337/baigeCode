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
    Long id;
    Integer user_id;
    Integer problem_id;
    Integer status;
    @Column(columnDefinition = "TEXT")
    String sourceCode;
    Integer compiler;
    Timestamp submission_time;


}
