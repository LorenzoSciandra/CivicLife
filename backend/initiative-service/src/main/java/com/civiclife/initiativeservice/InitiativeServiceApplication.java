package com.civiclife.initiativeservice;

import com.civiclife.initiativeservice.repo.InitiativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(/*exclude = {DataSourceAutoConfiguration.class }*/)
@EnableMongoRepositories
public class InitiativeServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(InitiativeServiceApplication.class, args);
        System.out.println("Cazzo");
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
