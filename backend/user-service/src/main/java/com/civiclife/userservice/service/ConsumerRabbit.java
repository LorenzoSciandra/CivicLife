package com.civiclife.userservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.civiclife.userservice.controller.*;

@Service
@RabbitListener(queues = "oauth-queue")
public class ConsumerRabbit {

    private UserController userController;

    @RabbitHandler
    public void readMessage(String message) {
        userController.
        System.out.println("Received message: " + message);
    }
}
