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
    // reference to mongoDB key
    Long test_id;
}
