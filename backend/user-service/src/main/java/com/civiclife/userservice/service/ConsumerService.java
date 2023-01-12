package com.civiclife.userservice.service;

import com.civiclife.userservice.component.UserComponent;
import com.civiclife.userservice.model.StatusType;
import com.civiclife.userservice.model.User;
import com.civiclife.userservice.repo.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsumerService {

    public boolean createFromLogin( String email, String name, String surname) {
        Optional<User> optionalUser = userRepository.findById(email);

        if (optionalUser.isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setSurname(surname);
            user.setAdmin(false);
            user.setBirthDate(0);
            user.setDomicile("");
            user.setFiscalCode("");
            user.setResidence("");
            user.setStatus(StatusType.ACTIVE);
            user.setTelephonNumber(0);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    private final UserRepository userRepository;

    @Autowired
    public ConsumerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RabbitListener(queues = "oauth-queue")
    public void receivedMessage(UserComponent userComponent) {
        System.out.println("Messaggio ricevuto dalla coda Rabbit");
        if(createFromLogin(userComponent.getMail(), userComponent.getName(), userComponent.getSurname()))
            System.out.println("Utente creato: " + userComponent.getMail());
        else
            System.out.println("Utente già esistente: " + userComponent.getMail());
    }
}

