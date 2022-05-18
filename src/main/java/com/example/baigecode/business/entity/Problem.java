package com.example.baigecode.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Problem {
    @Id
    private Long id;
    private String description;
    private String title;
    private Integer difficulty;
    private Integer editorial_id;
    private Integer contest;
    private Boolean visibility;
    private Integer usersSolved;
    private Integer totalSubmissions;
    @ElementCollection
    private List<String> topics;
}
