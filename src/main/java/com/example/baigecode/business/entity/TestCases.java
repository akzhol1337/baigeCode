package com.example.baigecode.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Data
@Document
public class TestCases {
    @Id
    private String id;
    @Indexed(unique = true)
    private Long problem_id;
    private List<String> inputs;
    private List<String> outputs;

    public TestCases(Long problem_id, List<String> inputs, List<String> outputs) {
        this.problem_id = problem_id;
        this.inputs = inputs;
        this.outputs = outputs;
    }
}
