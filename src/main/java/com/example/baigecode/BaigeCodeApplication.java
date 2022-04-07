package com.example.baigecode;

import com.example.baigecode.business.entity.ExecutionData;
import com.example.baigecode.business.entity.TestCases;
import com.example.baigecode.persistance.repository.TestCasesRepository;
import org.buildobjects.process.ProcBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class BaigeCodeApplication{

    public static void main(String[] args) {
        SpringApplication.run(BaigeCodeApplication.class, args);
    }

    /*

    @Bean
    CommandLineRunner runner(TestCasesRepository repository){
        return args -> {
            TestCases testCases = new TestCases(0L, List.of("99 100", "3 4", "5 5", "10 10"), List.of("3", "7", "10", "20"));
            repository.insert(testCases);
        };
    }


     */
    @Bean
    Map<Integer, ExecutionData> executionCommand(){
        Map<Integer, ExecutionData> executionCommand = new HashMap<>();
        final String PATHTOSOURCES = "./src/main/resources/sources/";


        // 0 = cpp
        // 1 = python
        // 2 = java
        // more soon...


        executionCommand.put(0, new ExecutionData("g++ -std=c++17 ", ".cpp", "c++"));
        executionCommand.put(1, new ExecutionData("python3 ", ".py", "python"));
        executionCommand.put(3, new ExecutionData("javac ", "java", "java"));

        return executionCommand;
    }
}
