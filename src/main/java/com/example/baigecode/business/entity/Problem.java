package com.example.baigecode.business.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

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

}
