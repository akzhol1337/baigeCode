package com.example.baigecode.business.service;

import com.example.baigecode.business.entity.ExecutionData;
import com.example.baigecode.business.entity.Submission;
import com.example.baigecode.business.entity.TestCases;
import com.example.baigecode.persistance.repository.SubmissionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.internal.Timeout;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Proc;
import org.buildobjects.process.ProcBuilder;
import org.buildobjects.process.ProcResult;
import org.buildobjects.process.TimeoutException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class SubmissionService {
    private final SubmissionRepository submissionRepo;
    private final AmqpTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final MongoTemplate mongoTemplate;
    private final Map<Integer, ExecutionData> executionData;

    public Boolean addSubmissionToQueue(Submission submission) throws JsonProcessingException {
        rabbitTemplate.convertAndSend("executionQueue", objectMapper.writeValueAsString(submission));
        return true;
    }

    public String runTest(int compiler, String input) {
        try {
            writeFile("./src/main/resources/sources/input.txt", input);
        } catch (IOException e) {
            log.error("Error while writing input to file");
            return "Error while writing to file";
        }
        // ugly af code
        if(compiler == 0) {
            try {
                new ProcBuilder("g++").withArgs("-std=c++17").withArgs("./src/main/resources/sources/source.cpp").run();
                String output = new ProcBuilder("./a.out").withInput(input).run().getOutputString() + '\n';
                return output;
            } catch (TimeoutException e) {
                log.error("Time limit error");
                return "Time limit error";
            } catch(Exception e) {
                log.error("Runtime error");
                return "Runtime error";
            }
        } else if(compiler == 1) {
            try {
                String output = new ProcBuilder("python3").withArgs("./src/main/resources/sources/source.py").withInput(input).run().getOutputString();
                return output;
            } catch (TimeoutException e) {
                log.error("Time limit error");
                return "Time limit error";
            } catch (Exception e) {
                log.error("Runtime error");
                return "Runtime error";
            }
        } else if(compiler == 2) {
            try {
                new ProcBuilder("javac").withArgs("./src/main/resources/sources/source.java").run();
                String output = new ProcBuilder("java").withArgs("./src/main/resources/sources/source.java").withInput(input).run().getOutputString();
                return output;
            } catch (TimeoutException e) {
                log.error("Time limit error");
                return "Time limit error";
            } catch (Exception e) {
                log.error("Runtime error");
                return "Runtime error";
            }
        } else {
            return "not defined";
        }
    }

    private void writeFile(String pathToFile, String content) throws IOException {
        Path path = Paths.get(pathToFile);
        byte[] contentBytes = content.getBytes();

        Files.write(path, contentBytes);
    }

    private void saveSubmission(Submission submission){
        String sourceCode = submission.getSourceCode();
        StringBuilder formattedSourceCode = new StringBuilder();
        for(int i = 0; i < sourceCode.length(); i++){
            if(sourceCode.charAt(i) == '"'){
                formattedSourceCode.append("\\");
            }
            formattedSourceCode.append(sourceCode.charAt(i));
        }
        log.info("Adding submission #{} to db", submission.getId());
        submission.setSourceCode(String.valueOf(formattedSourceCode));
        submissionRepo.save(submission);
    }

    public void checkSubmission(Submission submission) throws IOException {
        saveSubmission(submission);
        writeFile("./src/main/resources/sources/source" + executionData.get(submission.getCompiler()).getFileExtension(), submission.getSourceCode());
        Query query = new Query();
        query.addCriteria(Criteria.where("problem_id").is(submission.getProblem_id()));
        List<TestCases> testCases = mongoTemplate.find(query, TestCases.class);

        if(testCases.size() > 1){
            throw new IllegalStateException("found different test cases set for one problem");
        }
        if(testCases.isEmpty()){
            throw new IllegalStateException("no test cases set for this problem");
        }
        log.info("Checking... ");
        List<String> inputs = testCases.get(0).getInputs();
        List<String> outputs = testCases.get(0).getOutputs();

        if(inputs.size() != outputs.size()){
            throw new IllegalStateException("different number of inputs and outputs");
        }
        submission.setStatus(-1337);
        for (int i = 0; i < inputs.size(); i++) {
            String output = runTest(submission.getCompiler(), inputs.get(i));
            if(!Objects.equals(output, outputs.get(i) + '\n')){
                log.info("Wrong answer on test: {}, Users output: {}, Correct output: {}", i, output, outputs.get(i));
                submission.setStatus(i);
                break;
            }
        }
        saveSubmission(submission);
    }

    public List<Submission> getAllSubmissions() {
        return submissionRepo.findAll();
    }

    public Optional<Submission> getSubmissionById(Long id) {
        return submissionRepo.findSubmissionById(id);
    }

    public List<Submission> getUserSubmissions(Long id) {
        return submissionRepo.findAllByUserId(id);
    }

    public List<Submission> getUserSubmissionsPage(Long id) {
        return submissionRepo.findAllByUserId(id, PageRequest.of(0, 5));
    }
}
