package com.example.baigecode.business.listener;

import com.example.baigecode.business.entity.Submission;
import com.example.baigecode.business.service.SubmissionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@EnableRabbit
@Slf4j
@RequiredArgsConstructor
public class RabbitMqListener {
    private final SubmissionService submissionService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "executionQueue")
    public void processExecuteQueue(String message){
        try {
            Submission submission = objectMapper.readValue(message, Submission.class);
            log.info("#1 Received: " + message + " , Thread: " + Thread.currentThread().getId());
            log.info("User's code: {}", submission.getSourceCode());
            submissionService.checkSubmission(submission);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
