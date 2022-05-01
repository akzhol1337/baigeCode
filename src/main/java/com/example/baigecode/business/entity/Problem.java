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
    Long id;
    String description;
    String title;
    Integer difficulty;
    Integer editorial_id;
    Integer contest;
    Boolean visibility;

}
