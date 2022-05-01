package com.example.baigecode.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExecutionData {
    private String command;
    private String fileExtension;
    private String name;
}
