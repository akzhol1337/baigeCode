package com.example.baigecode.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExecutionData {
    String command;
    String fileExtension;
    String name;
}
