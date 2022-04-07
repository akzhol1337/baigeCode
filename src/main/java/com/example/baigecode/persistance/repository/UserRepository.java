package com.example.baigecode.persistance.repository;

import com.example.baigecode.business.entity.aUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<aUser, Long> {
}
