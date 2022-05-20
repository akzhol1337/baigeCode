package com.example.baigecode.persistance.repository;

import com.example.baigecode.business.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    Problem getTitleById(Long id);
}
