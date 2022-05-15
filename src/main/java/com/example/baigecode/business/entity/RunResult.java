package com.example.baigecode.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RunResult {
    private String output;
    private Long executionTime;

    public RunResult(String output) {
        this.output = output;
    }
}
