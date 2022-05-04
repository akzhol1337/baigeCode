package com.example.baigecode.persistance.repository;

import com.example.baigecode.business.entity.BaigeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BaigeUser, Long> {
    Optional<BaigeUser> findByUsername(String username);
}
