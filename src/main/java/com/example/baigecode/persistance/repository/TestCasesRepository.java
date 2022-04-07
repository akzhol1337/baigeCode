package com.example.baigecode.persistance.repository;

import com.example.baigecode.business.entity.TestCases;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestCasesRepository extends MongoRepository<TestCases, String> {

    TestCases insert(TestCases testCases);
}
