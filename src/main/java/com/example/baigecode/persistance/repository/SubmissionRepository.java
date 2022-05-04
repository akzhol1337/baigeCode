package com.example.baigecode.persistance.repository;

import com.example.baigecode.business.entity.Submission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Submission save(Submission submission);
    //List<Submission> getAllSubmissionsByUser(String user, Pageable pageable);
    //List<Submission> getAllSubmissionsByUser(String user);

    //List<Submission> getAllSubmissions();
    //Page<Submission> getAllSubmissions(Pageable pageable);
    List<Submission> findAll();
    Optional<Submission> findSubmissionById(Long id);
    List<Submission> findAllByUserId(Long id);
    List<Submission> findAllByUserId(Long id, Pageable pageable);
}
