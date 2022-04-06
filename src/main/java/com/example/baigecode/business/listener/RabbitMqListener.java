package com.example.baigecode.business.listener;

import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;   
@Component
public class RabbitListener {
    @org.springframework.amqp.rabbit.annotation.RabbitListener(queues = "executeQueue")
    public void processExecuteQueue(String message)
}
