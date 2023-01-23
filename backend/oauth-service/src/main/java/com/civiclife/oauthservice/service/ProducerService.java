package com.civiclife.oauthservice.service;

import com.civiclife.oauthservice.component.UserComponent;
import com.civiclife.oauthservice.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${spring.rabbitmq.template.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.template.routingkey}")
    private String routingkey;

    public void sendMessage(UserComponent userComponent) {
        rabbitTemplate.convertAndSend(exchange,routingkey, userComponent);
    }
}
